package com.github.erf88.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.github.erf88.dao.DAO;
import com.github.erf88.model.Author;

@ManagedBean
@ViewScoped
public class AuthorBean {

	private Author author = new Author();
	private Integer authorId;

	public Author getAuthor() {
		return author;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	public void loadAuthorById() {
		this.author = new DAO<Author>(Author.class).findById(authorId);
	}

	public List<Author> getAuthors() {
		return new DAO<Author>(Author.class).findAll();
	}

	public String create() {
		System.out.println("Gravando autor " + this.author.getName());

		if (this.author.getId() == null) {
			new DAO<Author>(Author.class).save(this.author);
		} else {
			new DAO<Author>(Author.class).update(this.author);
		}

		this.author = new Author();

		return "book?faces-redirect=true";
	}

	public void load(Author author) {
		System.out.println("Carregando livro " + author.getName());
		this.author = author;
	}

	public void remove(Author author) {
		System.out.println("Removendo livro " + author.getName());

		new DAO<Author>(Author.class).delete(author);
	}

}
