package com.github.erf88.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> clazz;
	private EntityManager em;

	public DAO(Class<T> clazz, EntityManager em) {
		this.clazz = clazz;
		this.em = em;
	}

	public void save(T t) {
		em.persist(t);
	}

	public void delete(T t) {
		em.remove(em.merge(t));
	}

	public void update(T t) {
		em.merge(t);
	}

	public List<T> findAll() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T findById(Integer id) {
		T instancia = em.find(clazz, id);
		return instancia;
	}

	public int countAll() {
		long result = (Long) em.createQuery(String.format("select count(m) from %s m", clazz.getSimpleName())).getSingleResult();

		return (int) result;
	}

	public List<T> findAllPaginated(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}

}
