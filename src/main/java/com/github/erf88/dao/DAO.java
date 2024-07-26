package com.github.erf88.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.github.erf88.util.JPAUtil;

public class DAO<T> {

	private final Class<T> clazz;

	public DAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void save(T t) {

		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
		em.close();
	}

	public void delete(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
		em.close();
	}

	public void update(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		em.close();
	}

	public List<T> findAll() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));

		List<T> lista = em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

	public T findById(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = em.find(clazz, id);
		em.close();
		return instancia;
	}

	public int countAll() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery(String.format("select count(m) from %s m", clazz.getSimpleName())).getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> findAllPaginated(int firstResult, int maxResults) {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		em.close();
		return lista;
	}

}
