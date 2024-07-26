package com.github.erf88.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.erf88.model.User;
import com.github.erf88.util.JPAUtil;

public class UserDao {

	public boolean exists(User user) {
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Long> query = em.createQuery("select count(u) from User u where u.email = :email and u.password = :password", Long.class);
		
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		
		Long result = query.getSingleResult();
		em.close();

		return result > 0;
	}
	
}
