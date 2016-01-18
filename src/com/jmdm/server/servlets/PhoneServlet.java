package com.jmdm.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="phoneServlet", urlPatterns="/phone")
public class PhoneServlet extends HttpServlet {

	private static final long serialVersionUID = -7089567389769941856L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		out.println("Hi");
		out.println(req.getParameter("id"));
	}
}
