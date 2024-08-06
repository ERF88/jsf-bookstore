package com.github.erf88.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.erf88.log.Log;
import com.github.erf88.model.Sale;

@Log
public class SaleDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	private DAO<Sale> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Sale>(Sale.class, this.em);
	}

	public List<Sale> findAll() {
		return dao.findAll();
	}
	
}
