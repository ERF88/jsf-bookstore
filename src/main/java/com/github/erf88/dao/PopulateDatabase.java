package com.github.erf88.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.github.erf88.model.Author;
import com.github.erf88.model.Book;
import com.github.erf88.model.User;
import com.github.erf88.util.JPAUtil;

import javax.persistence.EntityManager;

public class PopulateDatabase {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		User admin = generateUser("admin@email.com", "123456");
		em.persist(admin);

		User user = generateUser("user@email.com", "654321");
		em.persist(user);
		
		Author assis = generateAuthor("Machado de Assis");
		em.persist(assis);

		Author amado = generateAuthor("Jorge Amado");
		em.persist(amado);

		Author coelho = generateAuthor("Paulo Coelho");
		em.persist(coelho);

		Book casmurro = generateBook("978-8-52-504464-8", "Dom Casmurro", "10/01/1899", BigDecimal.valueOf(24.90), assis);
		Book memorias = generateBook("978-8-50-815415-9", "Memorias Postumas de Bras Cubas", "01/01/1881", BigDecimal.valueOf(19.90), assis);
		Book quincas = generateBook("978-8-50-804084-1", "Quincas Borba", "01/01/1891", BigDecimal.valueOf(16.90), assis);

		em.persist(casmurro);
		em.persist(memorias);
		em.persist(quincas);

		Book alquemista = generateBook("978-8-57-542758-3", "O Alquimista", "01/01/1988", BigDecimal.valueOf(19.90), coelho);
		Book brida = generateBook("978-8-50-567258-7", "Brida", "01/01/1990", BigDecimal.valueOf(12.90), coelho);
		Book valkirias = generateBook("978-8-52-812458-8", "As Valkirias", "01/01/1992", BigDecimal.valueOf(29.90), coelho);
		Book maao = generateBook("978-8-51-892238-9", "O Diario de um Mago", "01/01/1987", BigDecimal.valueOf(9.90), coelho);

		em.persist(alquemista);
		em.persist(brida);
		em.persist(valkirias);
		em.persist(maao);

		Book capitaes = generateBook("978-8-50-831169-1", "Capitaes da Areia", "01/01/1937", BigDecimal.valueOf(6.90), amado);
		Book flor = generateBook("978-8-53-592569-9", "Dona Flor e Seus Dois Maridos", "01/01/1966", BigDecimal.valueOf(18.90), amado);

		em.persist(capitaes);
		em.persist(flor);

		em.getTransaction().commit();
		em.close();

	}

	private static User generateUser(String email, String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

	private static Author generateAuthor(String name) {
		Author author = new Author();
		author.setName(name);
		return author;
	}

	private static Book generateBook(String isbn, String title, String releaseDate, BigDecimal price, Author author) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setReleaseDate(parseDate(releaseDate));
		book.setPrice(price);
		book.addAuthor(author);
		return book;
	}

	@SuppressWarnings("unused")
	private static Calendar parseDate(String data) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
