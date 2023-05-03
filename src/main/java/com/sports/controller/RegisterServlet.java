package com.sports.controller;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sports.domain.IDao;
import com.sports.domain.ManagerDao;
import com.sports.domain.Manager;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//內部派送一個註冊資料頁面
		//1.透過Request物件參照出RequestDispatcher物件
		//持續原來的Request and Response
		RequestDispatcher disp=request.getRequestDispatcher("WEB-INF/pages/register.jsp"); //指向內部採用虛擬目錄 調用
		disp.forward(request, response);
	}
	//表單後送 進行會員註冊作業


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//透過Request封裝前端表單欄位
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String realName=request.getParameter("realName");
		String email=request.getParameter("email");
		
		if(userName == null || password == null || realName == null || email == null || 
				userName.isEmpty() || password.isEmpty() || realName.isEmpty() || email.isEmpty()){
			String message="請輸入完整資訊";
			request.setAttribute("message",message);
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
			return;
		}
		//裝成一個JavaBean物件
		Manager manager=new Manager();
		manager.setUserName(userName);
		manager.setPassword(password);
		manager.setRealName(realName);
		manager.setEmail(email);
		
		
		//TODO 擷取所有表單欄位
		//1.問出DataSource物件(連接工廠 應該形成一個個體物件 與共用)
		DataSource datasource=(DataSource)this.getServletContext().getAttribute("datasource");
		//2.建構自訂DAO 注入DataSource
		IDao<Manager,String> dao=new ManagerDao();

		//封裝表單欄位變成JavaBean 採用 DAO(Data Access Object)設計模式進行新增作業(Model)
		//注入DataSource形成依賴關係
		dao.setDataSource(datasource);
		//3.執行會員註冊新增作業
		String message="";
		try {
			//新增會員作業
			dao.insert(manager);
			message="會員註冊成功";
			String redirectPath = "login"; // 存儲要跳轉的頁面路徑
			response.setContentType("text/html; charset=UTF-8");
		    String script = "<script>alert('註冊成功，歡迎"+userName+"加入，請驗證帳號');window.location.href='" + redirectPath + "';</script>";
		    response.getWriter().println(script);
		    return;
		
		} catch (SQLException e) {			
			message="註冊失敗";
			//使用request持續狀態
			request.setAttribute("message", message);
			//調用View
			//持續原來的Request and Response
			RequestDispatcher disp=request.getRequestDispatcher("WEB-INF/pages/register.jsp"); //指向內部採用虛擬目錄 調用
			disp.forward(request, response);
		}
		
	}
	

}
