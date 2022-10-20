package com.hali.spring;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.ConvertingParameterAccessor;
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

public class TenantMongoQueryLookupStrategy implements QueryLookupStrategy {
    private final QueryLookupStrategy strategy;
    private final MongoOperations mongoOperations;
    private final QueryMethodEvaluationContextProvider evaluationContextProvider;
    private final TenantStore tenantStore;

    public TenantMongoQueryLookupStrategy(QueryLookupStrategy strategy,
                                              MongoOperations mongoOperations,
                                              QueryMethodEvaluationContextProvider evaluationContextProvider,
                                              TenantStore tenantStore) {
        this.strategy = strategy;
        this.mongoOperations = mongoOperations;
        this.evaluationContextProvider = evaluationContextProvider;
        this.tenantStore = tenantStore;
        
    }
    
	
	private Criteria getTenantCriteria() {
		
		
		 TenantStore tenantStore = ApplicationContextUtil.context().getBean(TenantStore.class);
		
		
		return Criteria.where(Constants.TENANT_ID).is(tenantStore.getTenantId());
	}

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
                                        NamedQueries namedQueries) {
        RepositoryQuery repositoryQuery = strategy.resolveQuery(method, metadata, factory, namedQueries);

        // revert to the standard behavior if requested
        if (method.getAnnotation(SkipTenanatFilter.class) != null) {
            return repositoryQuery;
        }

        if (!(repositoryQuery instanceof PartTreeMongoQuery)) {
            return repositoryQuery;
        }
        PartTreeMongoQuery partTreeQuery = (PartTreeMongoQuery) repositoryQuery;

        return new TenantPartTreeMongoQuery(partTreeQuery);
    }
   

    private class TenantPartTreeMongoQuery extends PartTreeMongoQuery {
        TenantPartTreeMongoQuery(PartTreeMongoQuery partTreeQuery) {
            super(partTreeQuery.getQueryMethod(), mongoOperations, new SpelExpressionParser(), evaluationContextProvider);
        }

        @Override
        protected Query createQuery(ConvertingParameterAccessor accessor) {
            Query query = super.createQuery(accessor);
            return withTenant(query);
        }

        @Override
        protected Query createCountQuery(ConvertingParameterAccessor accessor) {
            Query query = super.createCountQuery(accessor);
            return withTenant(query);
        }

        private Query withTenant(Query query) {
            return query.addCriteria(getTenantCriteria());
        }
    }
}