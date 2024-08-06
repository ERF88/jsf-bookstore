package com.github.erf88.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.erf88.log.Log;
import com.github.erf88.model.Author;

@Log
public class AuthorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	private DAO<Author> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Author>(Author.class, this.em);
	}

	public Author findById(Integer authorId) {
		return dao.findById(authorId);
	}

	public List<Author> findAll() {
		return dao.findAll();
	}

	public void save(Author author) {
		dao.save(author);
	}

	public void update(Author author) {
		dao.update(author);
	}

	public void delete(Author author) {
		dao.delete(author);
	}

}
