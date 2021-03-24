package com.boss.blueSpring.comment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.blueSpring.comment.model.service.CommentService;
import com.boss.blueSpring.comment.model.vo.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath+"/comment").length());
		
		try {
			CommentService service = new CommentService();
			
			// ====== 댓글 목록 조회 Controller ======
			if(command.equals("/selectList.do")) {
				int parentBoardNo = Integer.parseInt(request.getParameter("parentBoardNo"));
				
				List<Comment> cList = service.selectList(parentBoardNo);
				
				Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월 dd일 HH:mm").create();
				gson.toJson(cList, response.getWriter());		
				
			}
			
			// ====== 댓글 등록 Controller ======
			else if(command.equals("/insertComment.do")) {
				String comWriter = request.getParameter("commentWriter");
				String comContent = request.getParameter("commentContent");
				int parentBoardNo = Integer.parseInt(request.getParameter("parentBoardNo"));
				
				Comment comment = new Comment();
				comment.setMemberId(comWriter);
				comment.setComContent(comContent);
				comment.setParentBoardNo(parentBoardNo);
				
				int result = service.insertComment(comment);
				
				response.getWriter().print(result);
			}
			
			// ====== 댓글 수정 Controller ======
			else if(command.equals("/updateComment.do")) {
				int comNo = Integer.parseInt(request.getParameter("commentNo"));
				String comContent = request.getParameter("afterCommentContent");
				
				Comment comment = new Comment();
				comment.setComNo(comNo);
				comment.setComContent(comContent);
				
				int result = service.updateComment(comment);
				
				response.getWriter().print(result);			
			}
			
			// ====== 댓글 삭제 Controller ======
			else if(command.equals("/deleteComment.do")) {
				int comNo = Integer.parseInt(request.getParameter("commentNo"));
				
				int result = service.deleteComment(comNo);
				
				response.getWriter().print(result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
