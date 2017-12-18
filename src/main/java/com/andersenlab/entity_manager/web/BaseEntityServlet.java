package com.andersenlab.entity_manager.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.andersenlab.entity_manager.entity.AbstractEntity;
import com.andersenlab.entity_manager.entity.AbstractRepository;
import com.andersenlab.entity_manager.entity.RepositoryFactory;

abstract public class BaseEntityServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
    private final Logger logger = LogManager.getLogger(getEntityClass());
	abstract protected Class<? extends AbstractEntity> getEntityClass();
	private String entityName = getEntityClass().getSimpleName().toLowerCase();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug(request);
		HtmlHelper.setHeaders(response);
		PrintWriter writer = response.getWriter();
		if (request.getPathInfo() == null) {
			index(writer);
		} else {
			try {
				handleRequestWithParams(request, writer);
			} catch (Exception e) {
		        logger.error(e);
				writer.append("Error");
			}			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request);
		HtmlHelper.setHeaders(response);
		List<String> pathChunks = getPathChunks(request);
		try {
			PrintWriter writer = response.getWriter();
			if (!pathChunks.isEmpty()) {
				switch (pathChunks.get(0)) {
					case "create":
						create(request, writer);
						break;
					case "delete":
						delete(request, writer);
						break;
					default:
						throw new Exception("No such action");
				}
			}
		} catch (Exception e) {
	        logger.error(e);
			System.out.println(e.getMessage());
		}
	}
	
	private void handleRequestWithParams(HttpServletRequest request, PrintWriter writer) throws Exception {
		List<String> pathChunks = getPathChunks(request);
		if (!pathChunks.isEmpty() && pathChunks.get(0).equals("create")) {
			createForm(request, writer);
		} else {
			throw new Exception("There's no such action");
		}
	}
	
	private List<String> getPathChunks(HttpServletRequest request) {
		return new ArrayList<String>(Arrays.asList(request.getPathInfo().substring(1).split("/")));
	}

	private void index(PrintWriter writer) {
		HtmlHelper.appendLink(writer, "/EntityManager/index", "Back");
		HtmlHelper.appendLink(
				writer,
				"/EntityManager/" + getEntityClass().getSimpleName().toLowerCase() + "/create",
				"Create"
		);
		try {
			prepareList(writer);
		} catch (Exception e) {
	        logger.error(e);
			writer.append("Error");
		}
	}
	
	private void prepareList(PrintWriter writer) {
		try {
			AbstractRepository repository = RepositoryFactory.createRepository(entityName);
			writer.append("List:</br>");
			for (AbstractEntity iter: repository.findAll(getEntityClass())) {
				writer.append(iter.toString());
				deleteForm(writer, iter.getId());
			}
		} catch (Exception e) {
	        logger.error(e);
			writer.append("Error");
		}
	}
	
	private void createForm(HttpServletRequest request, PrintWriter writer) {
		writer.append("Create " + entityName);
		HtmlHelper.appendCreateForm(
			writer,
			"/EntityManager/" + entityName + "/create"
		);
	}
	
	private void deleteForm(PrintWriter writer, int id) {
		HtmlHelper.appendDeleteForm(
			writer,
			"/EntityManager/" + entityName + "/delete",
			id
		);
	}
	
	private void create(HttpServletRequest request, PrintWriter writer) throws Exception {
		List<String> params = new ArrayList<>(
				Arrays.asList(request.getParameter("entity").split(" "))
		);
		AbstractRepository repository = RepositoryFactory.createRepository(entityName);
		repository.create(params);
		writer.append("Success");
		HtmlHelper.appendLink(writer, "/EntityManager/" + entityName, "Back");
	}

	private void delete(HttpServletRequest request, PrintWriter writer) throws Exception {
		int entityId = Integer.parseInt(request.getParameter("id"));
		AbstractRepository repository = RepositoryFactory.createRepository(entityName);
		repository.remove(getEntityClass(), entityId);
		writer.append("Success");
		HtmlHelper.appendLink(writer, "/EntityManager/" + entityName, "Back");
	}
}
