package com.jmdm.server.servlets;

import static com.jmdm.server.entities.Tables.USERS;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.jmdm.server.beans.LoginBean;
import com.jmdm.server.entities.tables.records.UsersRecord;

@WebServlet(name="addPhoneServlet", urlPatterns="/addPhone")
public class AddPhoneServlet extends HttpServlet {

	private static final long serialVersionUID = -209340586207592411L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		
		String id = req.getParameter("id");
		boolean userAdded = addPhone(id);
		PrintWriter out = resp.getWriter();
		if (userAdded) {
			out.println("User added successfully");
		} else {
			out.println("User could not be added");
		}
	}
	
	private boolean addPhone(String id) {
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			UsersRecord user = context.newRecord(USERS);
			user.setUsername(id);
			user.setPassword("");
			user.setCompany("");
			user.setUserTypeId(2);
			user.store();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			LoginBean.closeConnection(conn);
		}
	}
}
