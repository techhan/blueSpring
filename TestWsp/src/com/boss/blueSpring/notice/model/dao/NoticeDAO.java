package com.boss.blueSpring.notice.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.boss.blueSpring.notice.model.vo.Attachment;
import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.notice.model.vo.PageInfo;;

public class NoticeDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	Properties prop = null;
	
	public NoticeDAO() {
		String fileName = NoticeDAO.class.getResource("/com/boss/blueSpring/sql/notice/notice-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 공지글 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}

		} finally {
			close(rset);
			close(stmt);
		}

		return listCount;
	}
	
	
	/** 공지사항 목록 조회
	 * @param conn
	 * @return list
	 * @throws Exception
	 */
	public List<Notice> selectList(Connection conn, PageInfo pInfo) throws Exception{
		List<Notice> list = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;    // 시작은 1부터 
			int endRow = startRow + pInfo.getLimit() - 1; // 10
						
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			// 오류 없을시 ArrayList 생성
			list = new ArrayList<Notice>();
			
			while(rset.next()) {
				
				Notice notice = new Notice();
				notice.setNoticeNo(  	rset.getInt("NOTICE_NO")  );
				notice.setNoticeTitle(  rset.getString("NOTICE_TITLE")  );
				notice.setMemberId(  	rset.getString("MEM_ID")  );
				notice.setNoticeViews(  	rset.getInt("NOTICE_VIEWS")  );
				notice.setNoticeCrtDt(  rset.getTimestamp ("NOTICE_CRT_DT")  );
				
				list.add(notice);
				
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	
	/** 공지글 상세조회 DAO
	 * @param conn
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice selectNotice(Connection conn, int noticeNo) throws Exception{
		Notice notice = null;
		
		String query = prop.getProperty("selectNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				notice = new Notice();
				
				notice.setNoticeNo( rset.getInt("NOTICE_NO")   );
				notice.setNoticeTitle(  rset.getString("NOTICE_TITLE") );
				notice.setNoticeContent( rset.getString("NOTICE_CONTENT") );
				notice.setMemberId( rset.getString("MEM_ID") );
				notice.setNoticeViews( rset.getInt("NOTICE_VIEWS"));
				notice.setNoticeCrtDt( rset.getTimestamp("NOTICE_CRT_DT"));
				
			}
			
			
			
		} finally {
			close(rset);
			close(pstmt);
		}

		
		
		return notice;
	}


	/** 공지글 조회수 증가 DAO
	 * @param conn
	 * @param notice
	 * @return result
	 */
	public int increaseReadCount(Connection conn, int noticeNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,	noticeNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}

		return result;
	}

	
	/** 다음 공지글 번호 조회 DAO
	 * @param conn
	 * @return noticeNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		int noticeNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				noticeNo = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		return noticeNo;
	}
	
	/** 공지글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertNotice(Connection conn, Map<String, Object> map) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (int)map.get("noticeNo") );
			pstmt.setString(2, (String)map.get("noticeTitle") );
			pstmt.setString(3, (String)map.get("noticeContent") );
			pstmt.setInt(4, (int)map.get("noticeWriter") );
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	

	/** 파일 정보 삽입 DAO
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception {

		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getParentNoticeNo());
			pstmt.setInt(4, at.getFileLevel());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 공지글에 포함된 이미지 목록 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectNoticeFiles(Connection conn, int noticeNo) throws Exception{
		
		List<Attachment> fList = null;
		
		String query = prop.getProperty("selectNoticeFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment(
							rset.getInt("NOTICE_IMG_NUM"),
							rset.getString("C_FILE_NAME"),
							rset.getInt("FILE_LEVEL"));

				at.setFilePath(rset.getString("FILE_PATH") );
				
				fList.add(at);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}

	/** 공지글 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateNotice(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("noticeTitle") );
			pstmt.setString(2, (String)map.get("noticeContent") );
			pstmt.setInt(3, (int)map.get("noticeNo") );
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 파일 정보 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, Attachment newFile) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("updateAttachment");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 공지사항 상태 변경 DAO
	 * @param conn
	 * @param noticeNo
	 * @return
	 * @throws Exception
	 */
	public int updateNoticeStatus(Connection conn, int noticeNo) throws Exception{
		
		int result = 0;
		
		String query = prop.getProperty("updateNoticeStatus");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
}
