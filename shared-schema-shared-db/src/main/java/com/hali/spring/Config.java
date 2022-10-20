package com.hali.spring;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(repositoryFactoryBeanClass = TenantMongoRepositoryFactoryBean.class)
public class Config {

	@Bean(name = "tenantStore")
	@Scope(scopeName = "prototype")
	public TenantStore tenantStore() {
		return new TenantStore();
	}

	@Primary
	@Bean(name = "proxiedThreadLocalTargetSource")
	public ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
		ProxyFactoryBean result = new ProxyFactoryBean();
		result.setTargetSource(threadLocalTargetSource);
		return result;
	}

	@Bean(destroyMethod = "destroy")
	public ThreadLocalTargetSource threadLocalTenantStore() {
		ThreadLocalTargetSource result = new ThreadLocalTargetSource();
		result.setTargetBeanName("tenantStore");
		return result;
	}
	
	  @Bean
	  public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory , TenantStore tenantStore ) throws Exception {
	    ExtendedMongoTemplate mongoTemplate = new ExtendedMongoTemplate(mongoDbFactory, tenantStore);
	    return mongoTemplate;
	  }
}
