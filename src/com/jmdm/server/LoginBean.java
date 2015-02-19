package com.jmdm.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@ManagedBean
@RequestScoped
public class LoginBean {

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Connection getDbConnection() {
		System.out.println("in getDbConnection()");
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/sqlite");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public String login() {
		System.out.println("in login()");
		if (username == null || password == null) {
			return "index";
		}
		
		try {
			Connection conn = getDbConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from users");
			while (rs.next()) {
				String name = rs.getString("username");
				String pwd = rs.getString("password");
				if (username.equals(name) && password.equals(pwd)) {
					return "admin";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if (username.equals("admin") && password.equals("admin")) {
			return "admin";
		}*/
		
		return "index";
	}
}
