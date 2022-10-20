package com.hali.spring;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.CodeSignature;
//import org.bson.Document;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import lombok.RequiredArgsConstructor;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class RepositoryAspect {
//
//	private final TenantStore tenantStore; 
//
//	@Pointcut("execution(* org.springframework.data.mongodb.core.MongoTemplate.find*(..))()")
//	public void find() {
//	}
//
//	@Pointcut("execution(* org.springframework.data.mongodb.core.MongoTemplate.doFin*(..))()")
//	public void doFind() {
//	}
//	
//	@Pointcut("execution(* org.springframework.data.mongodb.core.MongoTemplate.*(..))()")
//	public void all() {
//	}
//
//	@Before("all()")
//	public void before(JoinPoint joinPoint) throws Throwable {
//		Object[] args = joinPoint.getArgs();
//
//		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
//		String[] names = codeSignature.getParameterNames();
//
//		Object arg;
//		for (int i = 0; i < names.length; i++) {
//			if(names[i].contains("query")) {
//				arg = args[i];
//
//				if (arg instanceof Query) {
//					Query query = (Query) arg;
//
//					Criteria newCriteria = Criteria
//							.where("tenantId").is(tenantStore.getTenantId());
//
//					query.addCriteria(newCriteria);
//
//					return;
//				}
//				else if (arg instanceof Document) {
//					Query query = (Query) arg;
//
//					Criteria newCriteria = Criteria
//							.where("tenantId").is(tenantStore.getTenantId());
//
//					query.addCriteria(newCriteria);
//
//					return;
//				}
//
//			}
//		}
//	}
//}