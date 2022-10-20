package com.hali.spring;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

public class TenantMongoRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends MongoRepositoryFactoryBean<T, S, ID> {
	private final TenantStore tenantStore;

    public TenantMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface,TenantStore tenantStore) {
        super(repositoryInterface);
        this.tenantStore = tenantStore;
    }

    @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return new TenantMongoRepositoryFactory(operations , tenantStore);
    }
}