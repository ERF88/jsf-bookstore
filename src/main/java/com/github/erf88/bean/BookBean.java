package com.github.erf88.bean;

import com.github.erf88.model.Author;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.github.erf88.dao.DAO;
import com.github.erf88.model.Book;

@ManagedBean
@ViewScoped
public class BookBean {

	private Book book = new Book();
	private Integer bookId;
	private Integer authorId;
	private List<Book> books;

	public Book getBook() {
		return book;
	}
	
	public List<Book> getBooks() {
		DAO<Book> dao = new DAO<Book>(Book.class);
		if(this.books == null) {
			this.books = dao.findAll();
		}
		return books;
	}
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	public void loadBookById() {
		this.book = new DAO<Book>(Book.class).findById(bookId);
	}
	
	public List<Author> getAuthors() {
		return new DAO<Author>(Author.class).findAll();
	}

	public List<Author> getBookAuthors() {
		return book.getAuthors();
	}
	
	public void addAuthor() {
		Author author = new DAO<Author>(Author.class).findById(this.authorId);
		this.book.addAuthor(author);
	}
	
	public void create() {
		System.out.println("Criando livro " + this.book.getTitle());

		if (book.getAuthors().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		DAO<Book> dao = new DAO<Book>(Book.class);
		if(this.book.getId() == null) {
			dao.save(this.book);
			this.books = dao.findAll();
		} else {
			dao.update(this.book);
		}
		
		this.book = new Book();
	}
	
	public void load(Book book) {
		System.out.println("Carregando livro " + book.getTitle());
		this.book = book;
	}
	
	public void remove(Book book) {
		System.out.println("Removendo livro " + book.getTitle());
		
		new DAO<Book>(Book.class).delete(book);
	}
	
	public void removeAuthor(Author author) {
		this.book.removeAuthor(author);
	}
	
	public String formAuthor() {
		System.out.println("Chamando o formulário do Autor");
		return "author?faces-redirect=true";
	}
	
	public void startsWithDigitOne(FacesContext fc, UIComponent component, Object object) throws ValidatorException {
		String value = object.toString();
		if(!value.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}
	}
	
}
