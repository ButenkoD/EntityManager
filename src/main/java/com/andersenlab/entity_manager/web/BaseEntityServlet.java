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
        logger.error(request);
		try {
			if (request.getPathInfo() == null) {
				index(request, response);
			} else {
				handleRequestWithParams(request, response);
			}
		} catch (Exception e) {
	        logger.error(e);
			response.getWriter().append("Error: " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request);
		List<String> pathChunks = getPathChunks(request);
		try {
			if (!pathChunks.isEmpty()) {
				switch (pathChunks.get(0)) {
					case "create":
						create(request, response);
						break;
					case "delete":
						delete(request, response);
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
	
	private void handleRequestWithParams(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> pathChunks = getPathChunks(request);
		if (!pathChunks.isEmpty() && pathChunks.get(0).equals("create")) {
			createForm(request, response);
		}
	}
	
	private List<String> getPathChunks(HttpServletRequest request) {
		return new ArrayList<String>(Arrays.asList(request.getPathInfo().substring(1).split("/")));
	}

	private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("template", "list");
		request.setAttribute("entityName", entityName);
		request.setAttribute("list", prepareList());
		request.getRequestDispatcher("/templates/base.jsp").forward(request, response);
	}

	private List<? extends AbstractEntity> prepareList() {
		try {
			AbstractRepository repository = RepositoryFactory.createRepository(entityName);
			return repository.findAll(getEntityClass());
		} catch (Exception e) {
	        logger.error(e);
			return null;
		}
	}

	private void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("template", "createForm");
		request.setAttribute("entityName", entityName);
		request.getRequestDispatcher("/templates/base.jsp").forward(request, response);
	}
	
	private void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> params = new ArrayList<>(
				Arrays.asList(request.getParameter("entity").split(" "))
		);
		AbstractRepository repository = RepositoryFactory.createRepository(entityName);
		repository.create(params);
		request.setAttribute("entityName", entityName);
		request.getRequestDispatcher("/templates/success.jsp").forward(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int entityId = Integer.parseInt(request.getParameter("id"));
		AbstractRepository repository = RepositoryFactory.createRepository(entityName);
		repository.remove(getEntityClass(), entityId);
		request.setAttribute("entityName", entityName);
		request.getRequestDispatcher("/templates/success.jsp").forward(request, response);
	}
}
