package com.github.erf88.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.erf88.dao.AuthorDao;
import com.github.erf88.model.Author;
import com.github.erf88.transaction.Transaction;

@Named
@ViewScoped
public class AuthorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AuthorDao dao;
	private Author author = new Author();
	private Integer authorId;
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	public void loadAuthorById() {
		this.author = this.dao.findById(authorId);
	}

	public List<Author> getAuthors() {
		return this.dao.findAll();
	}

	@Transaction
	public String create() {
		System.out.println("Gravando autor " + this.author.getName());

		if (this.author.getId() == null) {
			this.dao.save(this.author);
		} else {
			this.dao.update(this.author);
		}

		this.author = new Author();

		return "book?faces-redirect=true";
	}

	public void load(Author author) {
		System.out.println("Carregando livro " + author.getName());
		this.author = author;
	}

	@Transaction
	public void remove(Author author) {
		System.out.println("Removendo livro " + author.getName());

		this.dao.delete(author);
	}

}
