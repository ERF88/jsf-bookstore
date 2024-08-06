package com.github.erf88.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.erf88.log.Log;
import com.github.erf88.model.Book;

@Log
public class BookDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	private DAO<Book> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Book>(Book.class, this.em);
	}

	public void save(Book book) {
		dao.save(book);
	}

	public void delete(Book book) {
		dao.delete(book);
	}

	public void update(Book book) {
		dao.update(book);
	}

	public List<Book> findAll() {
		return dao.findAll();
	}

	public Book findById(Integer id) {
		return dao.findById(id);
	}
	
}
