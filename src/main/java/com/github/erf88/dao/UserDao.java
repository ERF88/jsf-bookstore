package com.github.erf88.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.erf88.log.Log;
import com.github.erf88.model.User;

@Log
public class UserDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public boolean exists(User user) {
		TypedQuery<Long> query = entityManager.createQuery("select count(u) from User u where u.email = :email and u.password = :password", Long.class);
		
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		
		Long result = query.getSingleResult();
		entityManager.close();

		return result > 0;
	}
	
}
