package com.sports.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class ManagerDao implements IDao<Manager,String>{
	private DataSource datasource;

	//實現新增作業
	@Override
	public boolean insert(Manager source)throws SQLException {
		//必須要有連接物件
		//1.借助注入進來的DataSource生產一條連接物件(同時開啟連接 連接上資料庫)
		if(this.datasource==null) {
			throw new SQLException("尚未注入連接工廠DataSource....");
		}
		//取出連接物件(介面多型化 才能具有相容性)--具有開啟connection open
		Connection connection=datasource.getConnection();
		//1.準備進入新增作業....透過連接物件問出具有參數設定PreparedStatement
		String sql="insert into manager(username,password,realname,email) values(?,?,?,?)";
		PreparedStatement st=connection.prepareStatement(sql);
		//設定參數
		st.setString(1,source.getUserName());
		st.setString(2,source.getPassword());
		st.setString(3, source.getRealName());
		st.setString(4, source.getEmail());
		try {
			//執行新增
			int affect=st.executeUpdate(); //執行新增或者修改或者刪除
		}finally {
			connection.close();
		}
		return true;
	}

	//DataSource 連接工廠物件 因為資料伺服器與資料庫與登入帳號與密碼 都是應用系統執行階段確認下來的
	//採用屬性注入 Property Injection 
	@Override
	public void setDataSource(DataSource datasource) {
		this.datasource=datasource;
	}
	
	
	
	@Override
	public Manager selectForObject(String key) throws SQLException{
		Manager manager=null;
		String[] items=key.split(",");
		Connection connection = datasource.getConnection();
		String sql="Select count(*) as counter From manager where username=? and password=?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, items[0]);
		st.setString(2, items[1]);
		
		ResultSet rs = st.executeQuery();		
		rs.next();
		if(rs.getInt("counter")>0) {
			manager = new Manager();
			manager.setUserName(items[0]);
			manager.setPassword(items[1]);
		}
		
		connection.close();		
		return manager;
	}

}