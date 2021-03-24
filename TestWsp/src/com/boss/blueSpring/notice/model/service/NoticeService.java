package com.boss.blueSpring.notice.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.close;
import static com.boss.blueSpring.common.JDBCTemplate.commit;
import static com.boss.blueSpring.common.JDBCTemplate.getConnection;
import static com.boss.blueSpring.common.JDBCTemplate.rollback;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.notice.model.dao.NoticeDAO;
import com.boss.blueSpring.notice.model.exception.FileInsertFailedException;
import com.boss.blueSpring.notice.model.vo.Attachment;
import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.notice.model.vo.PageInfo;

public class NoticeService {

	private NoticeDAO dao = new NoticeDAO();

	/** 페이징 처리 Service
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception{
		Connection conn = getConnection();
		
		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
									// t          f
		
		//DB에서 전체 게시글 수를 조회
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		// 얻어온 현재 페이지와 DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성
		return new PageInfo(currentPage, listCount);
		
	}
	
	/** 공지사항 목록 조회
	 * @return list
	 * @throws Exception
	 */
	public List<Notice> selectList(PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		List<Notice> list = dao.selectList(conn, pInfo);
		close(conn);
		return list;
	}

	/** 공지글 상세조회 Service
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice selectNotice(int noticeNo) throws Exception{

		Connection conn = getConnection();
		Notice notice = dao.selectNotice(conn, noticeNo);
		
		if(notice != null) { //DB에서 조회 성공 시 
			
			// 조회수 증가
			int result = dao.increaseReadCount(conn, noticeNo);
			 
			if(result > 0) {
				commit(conn);
				
				// 반환되는 Board 데이터에는 조회수가 증가되어 있지 않기 때문에
				// 조회수를 1등가 시켜줌
				notice.setNoticeViews( notice.getNoticeViews() + 1);
				
			}
			else	rollback(conn);
		}
		
		close(conn);
		return notice;
	}


	/** 공지글 등록 Service (공지글 등록 + 파일 업로드)
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertNotice(Map<String, Object> map) throws Exception{

		Connection conn = getConnection();
		
		int result = 0;
		
		// 1. 공지글 번호 얻어오기       -> 다수의 회원이 사용하니까      중복문제해결, 성공적으로 insert됐을 때 내가 삽입ㄹ한 게시글로 리다이랙트할 수 있음
		int noticeNo = dao.selectNextNo(conn);
		
		if(noticeNo > 0) {  //잘 얻어왔을 때 
			// 얻어온 게시글 번호를 map에 추가(게시글, 파일정보 삽입 DAO에서 사용하기 위해)
			map.put("noticeNo", noticeNo);

			// 2. 글 제목/내용 크로스 사이트 스크립팅 방지 처리              //태그 사용해서 망치는거!!
			String noticeTitle = (String)map.get("noticeTitle");
			String noticeContent = (String)map.get("noticeContent");
			
			noticeTitle =  replaceParameter(noticeTitle);
			noticeContent = replaceParameter(noticeContent);
			
			// 3. 글 내용 개행문자  \r\n -> <br> 변경 처리
			noticeContent = noticeContent.replaceAll("\r\n", "<br>");
			
			// 처리된 내용을 다시 map에 추가
			map.put("noticeTitle", noticeTitle);
			map.put("noticeContent", noticeContent);
			 
			try {
			
				// 4. 게시글 부분(제목, 내용, 카테고리)만 BOARD 테이블에 삽입하는 DAO 호출
				result = dao.insertNotice(conn, map);
				
				// 5. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
				if(result > 0 && !fList.isEmpty()) {
					
					result = 0; // result 재활용을 위해 0으로 초기화
					
					// fList의 요소를 하나씩 반복 접근하여
					//DAO 메소드를 반복 호출해 정보를 삽입함.
					for(Attachment at : fList) {          ///\사진이 4장이면 for문 4번 돌아가는거임
						
						// 파일 정보가 저장된 Attachment 객체에 
						// 해당 파일이 작성된 게시글 번호를 추가 세팅
						at.setParentNoticeNo(noticeNo);
						
						result = dao.insertAttachment(conn, at);
						
						if(result == 0) { // 파일 정보 삽입 실패
							//break; //보류
							
							// 강제로 예외 발생
							throw new FileInsertFailedException("파일 정보 삽입 실패");
						}
					}
				}
				
			}catch (Exception e) {
				// 4,5번에 대한 추가 작업
				// 게시글 또는 파일 정보 삽입 중 에러 발생 시 
				// 서버에 저장된 파일을 삭제하는 작업이 필요.
				
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(!fList.isEmpty()) {
					for(Attachment at : fList) {
						
						String filePath = at.getFilePath();
						String fileName = at.getFileName();
						
						File deleteFile = new File(filePath + fileName);
						
						if(deleteFile.exists()) {
							// 해당 경로에 해당 파일이 존재하면 
							deleteFile.delete(); //해당 파일 삭제
						}
					}
				}
				
				// 에러 페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
				throw e;
			}
			
			
			// 6. 트랜잭션 처리
			if(result > 0) {
				commit(conn);
				
				// 삽입 성공 시 상세 조회 화면으로  이동해야되기 때문에 
				// 글번호를 반환할 수 있도록 result에 boardNo를 대입
				result = noticeNo;
				
			}else {
				rollback(conn);
			}
		
		}
		
		
		// 7. 커넥션 반환
		close(conn);
		
		// 8. 결과 반환
		return result;
	}


	// 크로스 사이트 스크립팅 방지 메소드
	private String replaceParameter(String param) {
		
		String result = param;    
		
		if(param != null) {   //result, param 상관없음
			result = result.replaceAll("&", "&amp;");	
			result = result.replaceAll("<", "&lt;");	
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		
		return result;
	}

	/** 공지글에 포함되는 이미지 목록 조회 Service
	 * @param boardNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectNoticeFiles(int noticeNo) throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> fList = dao.selectNoticeFiles(conn, noticeNo);
		
		close(conn);
		
		return fList;
	}

	/** 공지글 수정 화면 출력용 Service
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice updateView(int noticeNo) throws Exception{

		Connection conn = getConnection();
		
		// 이전에 만들어둔 상세조회 DAO 호출
		Notice notice = dao.selectNotice(conn, noticeNo);
		
		// textarea 출력을 위한 개행문자 변경
		notice.setNoticeContent(notice.getNoticeContent().replaceAll("<br>", "\r\n"));
		
		close(conn);
		
		return notice;
	}


	/** 공지글 수정 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateNotice(Map<String, Object> map) throws Exception {

		Connection conn = getConnection();
		
		int result = 0; //service 수행 결과 저장 변수
		List<Attachment> deleteFiles = null; //삭제할 파일 정보 저장 변수 선언
		
		String noticeTitle = (String)map.get("noticeTitle");
		String noticeContent = (String)map.get("noticeContent");
		
		noticeTitle =  replaceParameter(noticeTitle);
		noticeContent = replaceParameter(noticeContent);
		
		// 3. 글 내용 개행문자  \r\n -> <br> 변경 처리
		noticeContent = noticeContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("noticeTitle", noticeTitle);
		map.put("noticeContent", noticeContent);
		
		try {
			// 3. 게시글 부분 수정 DAO  호출
			result = dao.updateNotice(conn, map);
			
			// 4. 게시글 수정 성공하고 fList가 비어있지 않으면
			// 	  파일 정보 수정 DAO 호출
			
			// 수정화면서 새로운 이미지가 업로드된 파일 정보만을 담고 있는 List
			List<Attachment> newFileList = (List<Attachment>)map.get("fList");            
			
			if( result > 0 && !newFileList.isEmpty()  ) {
				
				//DB에서 해당 게시글의 수정 전 파일 목록을 조회함.
				List<Attachment> oldFileList = dao.selectNoticeFiles(conn, (int)map.get("noticeNo"));
				
				result = 0; // result 재활용
				deleteFiles = new ArrayList<Attachment>(); //삭제될 파일 정보 저장 List
				
				// 새로운 이미지 정보 반복 접근
				for(Attachment newFile : newFileList) {
					
					// flag가 false인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되는 경우 -> update
					// flag가 true인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되지 않는 경우 -> insert
					boolean flag = true;
					
					// 기존 이미지 정보 반복 접근
					for(Attachment oldFile : oldFileList) {
					
						//새로운 이미지와 기존 이미지의 파일 레벨이 동일한 파일이 있다면
						if(newFile.getFileLevel() == oldFile.getFileLevel()) {
							
							// 기존 파일 삭제 List에 추가
							deleteFiles.add(oldFile);
							
							// 새 이미지 정보에 이전 파일 번호를 추가 ->파일 번호를 이용한 수정 진행 
							newFile.setFileNo( oldFile.getFileNo() );
							
							flag = false;
							
							break;
						}
					}
					
					// flag 값에 따라 파일 정보 insert 또는 update 수행
					if(flag) {
						result = dao.insertAttachment(conn, newFile);
					} else {
						result = dao.updateAttachment(conn, newFile);
					}
					
					// 파일 정보 삽입 또는 수정 중 실패 시
					if(result == 0) {
						//강제로 사용자 정의 예외 발생
						throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
					}
				}
			}
				
				
		}catch (Exception e) {
			// 게시글 수정 중 실패 또는 오류 발생 시 
			// 서버에 미리 저장되어있던 이미지 파일 삭제
			List<Attachment> fList = (List<Attachment>)map.get("fList");
			
			if(!fList.isEmpty()) {
				for(Attachment at : fList) {
					
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					
					File deleteFile = new File(filePath + fileName);
					
					if(deleteFile.exists()) {
						// 해당 경로에 해당 파일이 존재하면 
						deleteFile.delete(); //해당 파일 삭제
					}
				}
			}
			
			// 에러 페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
			throw e;
		}
		
		// 5. 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
		if(result > 0) {
			commit(conn);
			
			if(deleteFiles != null) {
				// DB 정보와 맞지 않는 파일(deleteFiles) 삭제 진행
				for(Attachment at : deleteFiles) {
					
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					
					File deleteFile = new File(filePath + fileName);
					
					if(deleteFile.exists()) {
						deleteFile.delete();
					}
				}
			}
			
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/** 공지글 삭제 Service(상태 N로 업데이트)
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int updateNoticeStatus(int boardNo) throws Exception {
		int result = 0;
		
		Connection conn = getConnection();
	
		result = dao.updateNoticeStatus(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
			
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	
	

	
	
	
	
	
}
