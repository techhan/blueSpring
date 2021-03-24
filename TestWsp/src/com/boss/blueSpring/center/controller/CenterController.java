package com.boss.blueSpring.center.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.blueSpring.center.model.service.CenterService;
import com.boss.blueSpring.center.model.vo.Center;
import com.google.gson.Gson;

@WebServlet("/center/*")
public class CenterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // 			/wsp/board/list.do
		String contextPath = request.getContextPath(); //   /wsp
		String command = uri.substring( (contextPath + "/center").length()); // /wsp/board를 잘라내는 구문
		
		String path = null;
		RequestDispatcher view = null;
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;		
		
		String errorMsg = null;
		
		try {
			CenterService service = new CenterService();
			
			if(command.equals("/centerForm.do")) {
				
				path = "/WEB-INF/views/center/centerMain.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);				
			}
			
			else if(command.equals("/selectCenterList.do")) {
				String sido = request.getParameter("sido");
				String guguns = "";
				String[] gugun = request.getParameterValues("gugun");
				
				List<Center> centerList = new ArrayList<Center>();
				
				// Array로 받아온 배열 정리하기.
				if(guguns != null) {
					for(String g : gugun) {
						guguns += "CENTER_AREA2 LIKE '%' || '" + g + "' || '%' OR ";
					}
					
					guguns = guguns.substring(0, guguns.lastIndexOf(" OR "));
				}
				
				List<Center> cList = service.selectCenterList(guguns, sido);
				
				Gson gson = new Gson();
				gson.toJson(cList, response.getWriter());
				
//				System.out.println(cList);
//				
//				response.getWriter().print(cList);				
			}

		} catch (Exception e) {
			e.printStackTrace();
			path="/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
