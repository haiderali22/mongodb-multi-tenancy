package com.hali.spring;

//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//
//import lombok.RequiredArgsConstructor;
//
////@Aspect
//@RequiredArgsConstructor
//public class TenantAspect {
//	
//	
//	private final TenantStore tenantStore; 
//	private final MongoTemplate template; 
//
//
//	@Pointcut("execution(public * find*(..))")
//	public void find() {
//	}
//
//	@Pointcut("execution(public * get*(..))")
//	public void get() {
//	}
//
//	@Pointcut("execution(* org.springframework.data.repository.Repository+.*(..))")
//	public void repository() {
//	}
//
//
//	@Around("repository() && ( find() || get())")
//	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//		
//
//		return null;
//	}
//}
