package com.github.erf88.transaction;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transaction
@Interceptor
public class TransactionManagerInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@AroundInvoke
	public Object executeTransaction(InvocationContext context) throws Exception {
		System.out.println("Abrindo transação");
		entityManager.getTransaction().begin();
		
		Object result = context.proceed();
		
		System.out.println("Fechando transação");
		entityManager.getTransaction().commit();
		
		return result;
	}
	
}
