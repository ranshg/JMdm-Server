package com.jmdm.server.beans;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.primefaces.context.RequestContext;

import static com.jmdm.server.entities.Tables.*;
import com.jmdm.server.entities.tables.records.UserTypesRecord;
import com.jmdm.server.entities.tables.records.UsersRecord;

@ManagedBean
@SessionScoped
public class AdminBean {

	UsersRecord[] users = null;
	private boolean usersUpdated = false;
	UserTypesRecord[] userTypes = null;
	private boolean userTypesUpdated = false;
	private boolean displayUsers = true;
	private boolean displayTypes = false;
	private String username = null;
	private String password = null;
	private int userType = 0;
	private String typeName = null;
	
	public String logout() {
		System.out.println("in logout()");
		return "index?faces-redirect=true";
	}
	
	public UsersRecord[] fetchUsers() {
		System.out.println("in fetchUsers()");
		users = null;
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
		usersUpdated = false;
		return users;
	}
	
	public UsersRecord[] getUsers() {
		System.out.println("in getUsers()");
		if (users == null || usersUpdated) {
			return fetchUsers();
		}
		
		return users;
	}
	
	public UserTypesRecord[] fetchUserTypes() {
		System.out.println("in fetchUserTypes()");
		userTypes = null;
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			userTypes = context.selectFrom(USER_TYPES)
					.fetch()
					.toArray(new UserTypesRecord[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LoginBean.closeConnection(conn);
		}
		userTypesUpdated = false;
		return userTypes;
	}
	
	public UserTypesRecord[] getUserTypes() {
		System.out.println("in getUserTypes()");
		if (userTypes == null || userTypesUpdated) {
			return fetchUserTypes();
		}
		
		return userTypes;
	}

	public String getStringType(int typeId) {
		userTypes = getUserTypes();
		for (UserTypesRecord userType : userTypes) {
			if (userType.getId() == typeId) {
				return userType.getTypeName();
			}
		}
		return null;
	}
	
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
	
	public int getUserType() {
		return userType;
	}
	
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String insertUser() {
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			UsersRecord user = context.newRecord(USERS);
			user.setUsername(username);
			user.setPassword(password);
			user.setCompany("");
			user.setUserTypeId(userType);
			user.store();
			usersUpdated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LoginBean.closeConnection(conn);
		}
		
		return "admin";
	}
	
	public String insertType() {
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			UserTypesRecord usrTyp = context.newRecord(USER_TYPES);
			usrTyp.setTypeName(typeName);
			usrTyp.store();
			userTypesUpdated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LoginBean.closeConnection(conn);
		}
		
		return "admin";
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
	
	public boolean getDisplayTypes() {
		return displayTypes;
	}
	
	public String addUser() {
		System.out.println("in addUser()");
		RequestContext.getCurrentInstance().execute("addUserDlg.show()");
		return null;
	}
	
	public String addType() {
		System.out.println("in addType()");
		RequestContext.getCurrentInstance().execute("addTypeDlg.show()");
		return null;
	}
}
