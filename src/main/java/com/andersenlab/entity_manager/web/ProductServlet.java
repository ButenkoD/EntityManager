package com.andersenlab.entity_manager.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersenlab.entity_manager.entity.AbstractEntity;
import com.andersenlab.entity_manager.entity.Product;

public class ProductServlet extends BaseEntityServlet {
	private static final long serialVersionUID = 2L;

	protected Class<? extends AbstractEntity> getEntityClass() {
		return Product.class;
	}
}
