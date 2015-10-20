package com.demo.spring.batch.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void persist(Object entity) {
		getEntityManager().persist(entity);
	}

	public void delete(Object entity) {
		getEntityManager().remove(entity);
	}
}
