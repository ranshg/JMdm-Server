package com.jmdm.server.beans;

import static com.jmdm.server.Tables.USERS;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.primefaces.context.RequestContext;

import com.jmdm.server.tables.records.UsersRecord;

@ManagedBean
@SessionScoped
public class AdminBean {

	private boolean displayUsers = true;
	private boolean displayTypes = false;
	
	public String logout() {
		System.out.println("in logout()");
		return "index?faces-redirect=true";
	}
	
	public UsersRecord[] getUsers() {
		UsersRecord[] users = null;
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			users = context.selectFrom(USERS)
					.orderBy(USERS.USERNAME)
					.fetch()
					.toArray(new UsersRecord[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LoginBean.closeConnection(conn);
		}
		
		return users;
	}
	
	public String showUsers() {
		System.out.println("in showUsers()");
		displayUsers = true;
		displayTypes = false;
		return "admin";
	}
	
	public String showTypes() {
		System.out.println("in showTypes()");
		displayUsers = false;
		displayTypes = true;
		return "admin";
	}
	
	public boolean getDisplayUsers() {
		System.out.println("in getDisplayUsers(), displayUsers = " + displayUsers);
		return displayUsers;
	}
	
	public String addUser() {
		System.out.println("in addUser()");
		RequestContext.getCurrentInstance().execute("addUserDlg.show()");
		return null;
	}
}
