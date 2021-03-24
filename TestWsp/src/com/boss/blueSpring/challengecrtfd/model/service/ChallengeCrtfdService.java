package com.boss.blueSpring.challengecrtfd.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.challenge.model.exception.FileInsertFailedException;
import com.boss.blueSpring.challengecrtfd.model.dao.ChallengeCrtfdDAO;
import com.boss.blueSpring.challengecrtfd.model.vo.Attachment;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;

public class ChallengeCrtfdService {

	private ChallengeCrtfdDAO dao = new ChallengeCrtfdDAO();
	
	/** 페이징 처리 Service
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception{
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		//DB에서 전체 게시글 수를 조회
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		// 얻어온 현재 페이지와 DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성
		return new PageInfo(currentPage, listCount);
		
	}

	/** 인증글 목록 조회 Service
	 * @param pInfo
	 * @param sort 
	 * @return list
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectList(PageInfo pInfo, String sort) throws Exception{
		Connection conn = getConnection();
		
		 sort = sort == null ? "" : sort;
		
		 String orderBy = null;
		 
		if(sort.equals("view")) {
			orderBy = " CHLNG_BRD_VIEWS DESC, ";
		}else {
			orderBy = "";
		}
		

		List<ChallengeCrtfd> list = dao.selectList(conn, pInfo, orderBy);   //, end
		
		close(conn);
		return list;
	}

	/** 인증글 상세조회 Service
	 * @param challengeCrtfdNo
	 * @return challengeCrtfd
	 * @throws Exception
	 */
	public ChallengeCrtfd selectChallengeCrtfd(int challengeCrtfdNo) throws Exception{

		Connection conn = getConnection();
		
		ChallengeCrtfd ChallengeCrtfd = dao.selectChallengeCrtfd(conn, challengeCrtfdNo);
		
		if(ChallengeCrtfd != null) { //DB에서 조회 성공 시 
			
			// 조회수 증가
			int result = dao.increaseReadCount(conn, challengeCrtfdNo);
			 
			if(result > 0) {
				commit(conn);
				
				// 반환되는 Board 데이터에는 조회수가 증가되어 있지 않기 때문에
				// 조회수를 1등가 시켜줌
				ChallengeCrtfd.setChlngBoardViews( ChallengeCrtfd.getChlngBoardViews() + 1);
				
			}
			else	rollback(conn);
		}
		
		
		close(conn);
		
		return ChallengeCrtfd;
	}

	/** 인증글에  포함되는 이미지 
	 * @param challengeCrtfdNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectChallengeCrtfdFiles(int challengeCrtfdNo) throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> fList = dao.selectChallengeCrtfdFiles(conn, challengeCrtfdNo);
		
		close(conn);
		
		return fList;
	}

	/** 인증 삭제 여부 (상테 업데이트) Service
	 * @param chlngNo
	 * @return result
	 * @throws Exception
	 */
	public int updateChCrFl(int chlngBoardNo) throws Exception {
		int result = 0;
		Connection conn = getConnection();
		result = dao.updateChCrFl(conn, chlngBoardNo);
		
		if(result > 0) {
			commit(conn);
			
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/** 인증글 수정 화면 출력용
	 * @param chlngBoardNo
	 * @return chlngBoard
	 */
	public ChallengeCrtfd updateView(int chlngBoardNo) throws Exception{
		Connection conn = getConnection();
		
		// 이전에 만들어둔 상세조회 DAO 호출
		ChallengeCrtfd challengeCrtfd = dao.selectChallengeCrtfd(conn, chlngBoardNo);
		
		// textarea 출력을 위한 개행문자 변경
		challengeCrtfd.setChlngBoardContent(challengeCrtfd.getChlngBoardContent().replaceAll("<br>", "\r\n"));
		
		close(conn);
		
		return challengeCrtfd;
	}



	/** 로그인한 회원이 참여하고있는 챌린지의 정보를을 조회하기 위한 Service
	 * @param memberNo
	 * @return joinInfo
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectjoinInfo(int memberNo) throws Exception {
		
		Connection conn = getConnection();
		
		List<Map<String, Object>> joinInfo = dao.selectjoinInfo(conn ,memberNo);
		
		close(conn);
		
		return joinInfo;
	}

	/** 인증글 등록 Service
	 * @param map
	 * @return result
	 */
	public int insertChallengeCrtfd(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		int result = 0;

		int chlngBoardNo = dao.selectNextNo(conn);
		System.out.println(chlngBoardNo);
		if (chlngBoardNo > 0) {
			map.put("chlngBoardNo", chlngBoardNo);

			String chlngCrContent = (String) map.get("chlngCrContent");

			
			chlngCrContent = replaceParameter(chlngCrContent);

			chlngCrContent = chlngCrContent.replaceAll("\r\n", "<br>");

			map.put("chlngContent", chlngCrContent);

			try {

				result = dao.insertChallengeCrtfd(conn, map);
				List<Attachment> cList = (List<Attachment>) map.get("fList");

				if (result > 0 && !cList.isEmpty()) {

					result = 0; 

					for (Attachment at : cList) { 

						at.setParentChNo(chlngBoardNo);

						result = dao.insertAttachment(conn, at); 
						//System.out.println("f : " + result);
						if (result == 0) { // 파일 정보 삽입 실패
							// break; //보류
							//System.out.println("e : " + result);
							// 강제로 예외 발생
							throw new FileInsertFailedException("파일 정보 삽입 실패");
						}
					}
				}

			} catch (Exception e) {
				List<Attachment> cList = (List<Attachment>) map.get("fList");

				if (!cList.isEmpty()) {
					for (Attachment at : cList) {

						String filePath = at.getFilePath();
						String fileName = at.getFileName();

						File deleteFile = new File(filePath + fileName);

						if (deleteFile.exists()) {
							// 해당 경로에 해당 파일이 존재하면
							deleteFile.delete(); // 해당 파일 삭제
						}
					}
				}

				// 에러 페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
				throw e;
			}

			// 6. 트랜잭션 처리
			if (result > 0) {
				commit(conn);

				// 삽입 성공 시 상세 조회 화면으로 이동해야되기 때문에
				// 글번호를 반환할 수 있도록 result에 boardNo를 대입
				result = chlngBoardNo;

			} else {
				rollback(conn);
			}

		}

		close(conn);

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
		

		/** 인증글 수정
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public int udpateChallengeCrtfd(Map<String, Object> map) throws Exception{
			Connection conn = getConnection();
			
			int result = 0;
			// 삭제할 파일 정보를 모아둘 List 변수 선언
			List<Attachment> deleteFiles = null;
						
						
			// 크로스 사이트 스크립팅 방지 처리
			map.put("chlngCrTitle", replaceParameter((String)map.get("chlngCrTitle")));
			map.put("chlngCrContent", replaceParameter((String)map.get("chlngCrContent")));
			
			// 개행문자 변경 처리
			map.put("chlngCrContent", ((String)map.get("chlngCrContent")).replaceAll("\r\n", "<br>") );
			
			try {
				result = dao.updateChallengeCr(conn, map);
				
				// DB에서 파일 정보 수정 
				// 게시글 수정 성공 + fList가 비어있지 않은 경우
				List<Attachment> newFileList =  (List<Attachment>)map.get("fList");
				
				if(result > 0 && !newFileList.isEmpty()) { // 게시글 수정 성공 시
					
					// 삭제할 파일 정보를 모아둘 List 객체 생성
					deleteFiles = new ArrayList<Attachment>();
					
					// DB에서 이전 파일 목록을 조회해옴
					// selectBoardFiles() 수행 시 DB 에서 FILE_PATH도 조회할 수 있도록 수정 필요함!!!!!!!!!!
					List<Attachment> oldFileList = dao.selectChallengeCrtfdFiles(conn, (int)map.get("chlngNo"));
					
					
					result = 0; // result 재활용을 위해 0 저장
					
					// 새 이미지 목록 반복 접근 
					for(Attachment newFile : newFileList) {
						newFile.setParentChNo((int)map.get("chlngNo"));
						boolean flag = true;
						
						// 이전 이미지 목록 반복 접근
						for(Attachment oldFile : oldFileList) {
							
							if(newFile.getFileLevel() == oldFile.getFileLevel()) {
								
								flag = false; // 수정 작업을 진행할 수 있도록 flag 값을 false로 변환
								
								deleteFiles.add(oldFile); // 삭제할 파일 목록에 oldfile 정보 추가
								
								// 새 이미지 정보에 이전 파일번호를 추가 -> 파일 번호를 통해 수정 진행
								newFile.setFileNo(oldFile.getFileNo());
								
								break;
							}
							
						}
						
						
						// 새 이미지의 파일레벨과 이전 이미지 중 파일레벨이 같은 것이 없을 경우 -> 삽입
						// 새 이미지의 파일레벨과 이전 이미지 중 파일레벨이 같은 것이 있을 경우 -> 수정
						if(flag) {
							result = dao.insertAttachment(conn, newFile);
						}else {
							result = dao.updateAttachment(conn, newFile);
						}
						
						
						
						// 삽입 또는 수정 중 한번이라도 실패 시 break
						if(result == 0 ) {
							// 강제로 예외 발생
							throw new FileInsertFailedException("파일 정보 삽입 실패");
						}
						
					}
				}
				
			}catch (Exception e) {
				// 파일 시스템에 저장된 이름으로 파일 객체 생성함
				for(Attachment at : (List<Attachment>)map.get("fList")) {
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					File failedFile = new File(filePath + fileName);
					
					if(failedFile.exists()) {
						failedFile.delete();
					}
				}
				
				throw e;
			}	
			
			
			// 트랜잭션 처리
			if(result > 0) {
				commit(conn);
				
				
				if(deleteFiles != null) {
				
					// 서버에 저장된 파일 중 수정되서 DB에 정보가 남지 않은 파일을 삭제
					for(Attachment at : deleteFiles) {
						System.out.println(at);
						String savePath = at.getFilePath();
						String saveFile = at.getFileName();
						File failedFile = new File(savePath + saveFile);
		
						if(failedFile.exists()) {
							failedFile.delete();
						}
					}	
				}
			}else {
				rollback(conn);
			}
			
			close(conn);
				
			return result;
			
		}
	
	
	
	
	
	
	
	
}
