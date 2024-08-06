package com.github.erf88.log;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Log
@Interceptor
public class RuntimeInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@AroundInvoke
	public Object executeTransaction(InvocationContext context) throws Exception {
		long before = System.currentTimeMillis();
		entityManager.getTransaction().begin();
		
		Object result = context.proceed();
		
		long after = System.currentTimeMillis();
		entityManager.getTransaction().commit();
		
		long runtime = after - before;
		System.out.println(String.format("Tempo de execução do método %s: %d milissegundos", context.getMethod().getName(), runtime));
		
		return result;
	}
	
}
