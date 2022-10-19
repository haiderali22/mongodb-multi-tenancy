package com.hali.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RepositoryAspect {
	
	private final TenantStore tenantStore; 
	
    @Before("execution(* org.springframework.data.mongodb.core.MongoTemplate.*(..))()")
    public void before(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Query) {
                Query query = (Query) arg;
                
            	Criteria newCriteria = Criteria
        				.where("tenantId").is(tenantStore.getTenantId());

            	query.addCriteria(newCriteria);
                
                return;
            }
        }
    }
}