package com.andersenlab.entity_manager.web;

import com.andersenlab.entity_manager.entity.AbstractEntity;
import com.andersenlab.entity_manager.entity.Customer;

public class CustomerServlet extends BaseEntityServlet {
	private static final long serialVersionUID = 2L;

	protected Class<? extends AbstractEntity> getEntityClass() {
		return Customer.class;
	}
}
