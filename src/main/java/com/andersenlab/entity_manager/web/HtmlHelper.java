package com.andersenlab.entity_manager.web;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class HtmlHelper {
	static void setHeaders(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
	}

	static void appendLink(PrintWriter writer, String href, String text) {
		writer.append("<a href=\""+href+"\">")
			.append(text)
			.append("</a>")
			.append("<br/>");
	}

	static void appendCreateForm(PrintWriter writer, String action) {
		writer.append("<form method=\"POST\" action=\"" + action + "\">");
		writer.append("<span>Create</span>");
		writer.append("<input name=\"entity\" type=\"text\"/><br/>");
		writer.append("<button type=\"submit\">create</button>");
		writer.append("</form>");
	}

	static void appendDeleteForm(PrintWriter writer, String action, int id) {
		writer.append("<form method=\"POST\" action=\"" + action + "\">");
		writer.append("<input name=\"id\" type=\"hidden\" value=\""+id+"\"/>");
		writer.append("<button type=\"submit\">delete</button>");
		writer.append("</form>");
	}
}
