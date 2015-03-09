package com.jmdm.server.beans;

import static com.jmdm.server.Tables.USERS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.jmdm.server.tables.records.UsersRecord;

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
	
	public static Connection getDbConnection() {
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

	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UsersRecord[] fetchUsers() {
		System.out.println("in fetchUsers()");
		UsersRecord[] users = null;
		Connection conn = null;
		try {
			conn = getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			users = context.selectFrom(USERS)
					.orderBy(USERS.USERNAME)
					.fetch()
					.toArray(new UsersRecord[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return users;
	}

	private boolean canLogin(UsersRecord user, int n) {
		if (!user.getUsername().equals(username) ||
				!user.getPassword().equals(password)) {
			return false; // illegal username or password
		}
		
		switch (n) {
		case 1:
			if (user.getUserTypeId() == 1) {
				return true;
			}
			
		case 2:
			if (user.getUserTypeId() == 1 || user.getUserTypeId() == 2) {
				return true;
			}
			
		case 3:
			if (user.getUserTypeId() == 1 || user.getUserTypeId() == 3) {
				return true;
			}
		}
		return false;
	}
	
	public String login(int n) {
		System.out.println("in login()");
		if (username == null || password == null) {
			return "index";
		}
		
		UsersRecord[] users = fetchUsers();
		for (UsersRecord user : users) {
			if (canLogin(user, n)) {
				switch (n) {
				case 1:
					return "admin?faces-redirect=true";
				
				case 2:
					return "phones?faces-redirect=true";
				}
			}
		}
		
		return "index";
	}
}
