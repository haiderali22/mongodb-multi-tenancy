package com.hali.spring;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

import java.util.Optional;

public class TenantMongoRepositoryFactory extends MongoRepositoryFactory {
    private final MongoOperations mongoOperations;
    private final TenantStore tenantStore;

    public TenantMongoRepositoryFactory(MongoOperations mongoOperations,TenantStore tenantStore) {
        super(mongoOperations);
        this.mongoOperations = mongoOperations;
        this.tenantStore = tenantStore;
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key,
                                                                   QueryMethodEvaluationContextProvider evaluationContextProvider) {
        Optional<QueryLookupStrategy> optStrategy = super.getQueryLookupStrategy(key,
                evaluationContextProvider);
        return Optional.of(createTenantQueryLookupStrategy(optStrategy.get(), evaluationContextProvider));
    }

    private TenantMongoQueryLookupStrategy createTenantQueryLookupStrategy(QueryLookupStrategy strategy,
                                                                                   QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return new TenantMongoQueryLookupStrategy(strategy, mongoOperations, evaluationContextProvider,tenantStore);
    }
}