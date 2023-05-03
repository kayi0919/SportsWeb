package com.sports.domain;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;

import javax.sql.DataSource;

//介面 規範CRUD方法架構 T-Entity class K-查詢Key型別
//配合Generic <>
public interface IDao<T,K> {
	//新增
	public default boolean insert(T source) throws SQLException {return false;};
	//查詢 單筆查詢
	public default T selectForObject(K key) throws SQLException {return null;};
	//多筆查詢
	public default List<T> selectForList(K key) throws SQLException{return null;}
	//修改
	public default boolean update(T source)throws SQLException {return false;}
	//刪除
	public default boolean delete(K key) throws SQLException {return false;}
	//注入連接物件的工廠的屬性(Dependency Injection-依賴注入) Property Injection
	public void setDataSource(DataSource datasource);
	
}
