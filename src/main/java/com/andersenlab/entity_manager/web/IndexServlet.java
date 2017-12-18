package com.andersenlab.entity_manager.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HtmlHelper.setHeaders(response);
		HtmlHelper.appendLink(response.getWriter(), "/EntityManager/customer", "Customers");
		HtmlHelper.appendLink(response.getWriter(), "/EntityManager/product", "Products");
		HtmlHelper.appendLink(response.getWriter(), "/EntityManager/purchase", "Purchases");
	}
}
