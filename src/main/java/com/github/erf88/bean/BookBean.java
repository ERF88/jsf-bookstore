package com.github.erf88.bean;

import com.github.erf88.model.Author;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.erf88.dao.AuthorDao;
import com.github.erf88.dao.BookDao;
import com.github.erf88.model.Book;
import com.github.erf88.transaction.Transaction;

@Named
@ViewScoped
public class BookBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BookDao bookDao;
	@Inject
	private AuthorDao authorDao;
	@Inject
	private FacesContext facesContext;
	
	private Book book = new Book();
	private Integer bookId;
	private Integer authorId;
	private List<Book> books;

	public Book getBook() {
		return book;
	}
	
	public List<Book> getBooks() {
		if(this.books == null) {
			this.books = this.bookDao.findAll();
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
		this.book = this.bookDao.findById(bookId);
	}
	
	public List<Author> getAuthors() {
		return this.authorDao.findAll();
	}

	public List<Author> getBookAuthors() {
		return book.getAuthors();
	}
	
	public void addAuthor() {
		Author author = this.authorDao.findById(this.authorId);
		this.book.addAuthor(author);
	}
	
	@Transaction
	public void create() {
		System.out.println("Criando livro " + this.book.getTitle());

		if (book.getAuthors().isEmpty()) {
			facesContext.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if(this.book.getId() == null) {
			this.bookDao.save(this.book);
			this.books = this.bookDao.findAll();
		} else {
			this.bookDao.update(this.book);
		}
		
		this.book = new Book();
	}
	
	public void load(Book book) {
		System.out.println("Carregando livro " + book.getTitle());
		loadBookById();
	}
	
	@Transaction
	public void remove(Book book) {
		System.out.println("Removendo livro " + book.getTitle());
		
		this.bookDao.delete(book);
		this.books = this.bookDao.findAll();
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
