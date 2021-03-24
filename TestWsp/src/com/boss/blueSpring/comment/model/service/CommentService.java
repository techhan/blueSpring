package com.boss.blueSpring.comment.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.close;
import static com.boss.blueSpring.common.JDBCTemplate.commit;
import static com.boss.blueSpring.common.JDBCTemplate.getConnection;
import static com.boss.blueSpring.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.boss.blueSpring.comment.model.dao.CommentDAO;
import com.boss.blueSpring.comment.model.vo.Comment;

public class CommentService {
	private CommentDAO dao = new CommentDAO();
	
	
	/** 댓글 목록 조회 Service
	 * @param parentBoardNo
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectList(int parentBoardNo) throws Exception {
		Connection conn = getConnection();
		
		List<Comment> cList = dao.selectList(conn, parentBoardNo);
		
		close(conn);
		
		return cList;
	}


	/** 댓글 등록 Service
	 * @param comment
	 * @return result
	 * @throws Exception
	 */
	public int insertComment(Comment comment) throws Exception {
		Connection conn = getConnection();
		
		comment.setComContent(replaceParameter(comment.getComContent()));		
		comment.setComContent(comment.getComContent().replaceAll("\n", "<br>"));
		
		int result = dao.insertComment(conn, comment);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	// 크로스 사이트 스크립트 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		
		return result;
	}


	/** 댓글 수정 Service
	 * @param comment
	 * @return result
	 * @throws Exception
	 */
	public int updateComment(Comment comment) throws Exception {
		Connection conn = getConnection();
		
		comment.setComContent(replaceParameter(comment.getComContent()));		
		comment.setComContent(comment.getComContent().replaceAll("\n", "<br>"));
		
		int result = dao.updateComment(conn, comment);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 댓글 삭제 Service
	 * @param comNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteComment(int comNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteComment(conn, comNo);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

}
