package com.sports.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sports.domain.DBConfig;
import com.sports.domain.AppConfig;
import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationHandler implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("應用系統啟動了....");
		//獲取聆聽對象 網站系統物件
		ServletContext application=sce.getServletContext();
		//建構 AppConfig個體物件 封裝配置web.xml初始化資訊
		//取出應用系統初始化參數配置
		String connectionString=application.getInitParameter("connectionString");
		AppConfig config=new AppConfig();
		config.setConnectionString(connectionString);
		//讓這一個config生命週期跟應用系統一直活著
		//狀態管理 具有Scope
		application.setAttribute("config", config);
		
		Properties properties=new Properties();
		//2.Load 資源檔xxx.properties
		//先建構串流開啟檔案  實際目錄???(跟應用系統用)
		InputStream is;
		try {
			is = new FileInputStream(application.getRealPath("/WEB-INF")+"/dbconfig.properties");
			properties.load(is);
			//讀取配置
			String url=properties.getProperty("mysql.url");
			String userName=properties.getProperty("mysql.username");
			String password=properties.getProperty("mysql.password");
			System.out.println(url);
			//封裝成物件 將物件進行狀態管理(應用系統範圍)
			DBConfig dbConfig=new DBConfig();
			dbConfig.setUrl(url);
			dbConfig.setUserName(userName);
			dbConfig.setPassword(password);
			//讓應用系統進行屬性參照
			application.setAttribute("dbconfig", dbConfig);
			//建構一個DataSource物件  形成應用系統共用一個連接工廠(工作區)架構
			MysqlDataSource dataSource=new MysqlDataSource();
			dataSource.setUrl(url);
			dataSource.setUser(userName);
			dataSource.setPassword(password); //內部使用自己的Driver class
			application.setAttribute("datasource",dataSource);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
