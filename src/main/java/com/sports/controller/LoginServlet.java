package com.sports.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import com.sports.domain.IDao;
import com.sports.domain.ManagerDao;
import com.sports.domain.Manager;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(userName == null || password == null || userName.isEmpty() || password.isEmpty()){
			String message="請輸入帳號密碼";
			request.setAttribute("message",message);
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			return;
		}
		
		DataSource datasource = (DataSource)this.getServletContext().getAttribute("datasource");
		IDao<Manager,String> dao = new ManagerDao();
		dao.setDataSource(datasource);
		
		String message="";
		try {
			Manager manager=dao.selectForObject(userName+","+password);
			if(manager!=null) {
				message="登入成功";
				HttpSession session = request.getSession(true);
				session.setAttribute("cred", userName);
				
				String redirectPath = "manager"; // 存儲要跳轉的頁面路徑
				response.setContentType("text/html; charset=UTF-8");
			    String script = "<script>alert('登入成功，歡迎"+userName+"');window.location.href='" + redirectPath + "';</script>";
			    response.getWriter().println(script);
			    return;
			}		
			else {				
				message="登入失敗";
				request.setAttribute("message",message);
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
