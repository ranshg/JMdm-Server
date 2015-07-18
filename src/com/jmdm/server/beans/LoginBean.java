package com.jmdm.server.beans;

import static com.jmdm.server.Tables.USERS;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.jmdm.server.dto.UserCreds;
import com.jmdm.server.tables.records.UsersRecord;

@ManagedBean
@RequestScoped
public class LoginBean {

	private int activeIndex = 0;
	private String username;
	private String password;
//	private String musername;
//	private String mpassword;
	
	public int getActiveIndex() {
		return activeIndex;
	}
	
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	/*public String getMusername() {
		return musername;
	}
	
	public void setMusername(String musername) {
		this.musername = musername;
	}*/
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/*public String getMpassword() {
		return mpassword;
	}
	
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}*/
	
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

	private boolean canLogin(UsersRecord user, UserCreds creds, int n) {
		System.out.println("in canLogin()");
		System.out.println("user.getUsername() = " + user.getUsername());
		System.out.println("user.getPassword() = " + user.getPassword());
		System.out.println("username = " + creds.getUsername());
		System.out.println("password = " + creds.getPassword());
		if (!user.getUsername().equals(creds.getUsername()) ||
				!user.getPassword().equals(creds.getPassword())) {
			System.out.println("returning false");
			return false; // illegal username or password
		}
		
		System.out.println("n = " + n);
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
		System.out.println("returning false(2)");
		return false;
	}
	
	public String login() {
		int n = activeIndex + 1;
		
		System.out.println("in login(), n = " + n);
		UsersRecord[] users = fetchUsers();
		UserCreds creds = null;
		
		switch (n) {
		case 1:
		case 2:
		case 3:
			if (username == null || password == null) {
				return "index";
			}
			creds = new UserCreds(username, password);
			break;
		}
		
		for (UsersRecord user : users) {
			if (canLogin(user, creds, n)) {
				switch (n) {
				case 1:
					return "admin?faces-redirect=true";
				
				case 2:
					return "phones?faces-redirect=true";
					
				case 3:
					return "store?faces-redirect=true";
				}
			}
		}
		
		return "index";
	}
}
