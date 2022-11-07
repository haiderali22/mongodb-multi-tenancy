package com.hali.spring;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.CursorPreparer;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.DeleteResult;

public class ExtendedMongoTemplate extends MongoTemplate {

	private final TenantStore store;

	public ExtendedMongoTemplate(MongoDatabaseFactory mongoDbFactory , TenantStore store) {
		super(mongoDbFactory);		
		this.store = store;
	}

	private Document getTenantCriteriaDocument() {
		return getTenantCriteria().getCriteriaObject();
	}
	
	private Criteria getTenantCriteria() {
		return Criteria.where(Constants.TENANT_ID).is(store.getTenantId());
	}

	@Override
	protected <T> T doFindOne(
			String collectionName,
			Document query,
			Document fields,
			CursorPreparer preparer,
			Class<T> entityClass) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFindOne(collectionName, query, fields, preparer, entityClass);
	}

	@Override
	protected <T> List<T> doFind(String collectionName, Document query, Document fields, Class<T> entityClass) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFind(collectionName, query, fields, entityClass);
	}


	@Override
	protected <T> List<T> doFindAndDelete(String collectionName, Query query, Class<T> entityClass) {
		query.addCriteria(getTenantCriteria());
		return super.doFindAndDelete(collectionName, query, entityClass);
	}
	@Override
	protected <T> T doFindAndModify(String collectionName, Document query, Document fields, Document sort,
			Class<T> entityClass, UpdateDefinition update, FindAndModifyOptions options) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFindAndModify(collectionName, query, fields, sort, entityClass, update, options);
	}

	@Override
	protected <T> T doFindAndRemove(String collectionName, Document query, Document fields, Document sort,
			Collation collation, Class<T> entityClass) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFindAndRemove(collectionName, query, fields, sort, collation, entityClass);
	}

	@Override
	protected <T> List<T> doFind(String collectionName, Document query, Document fields, Class<T> entityClass,
			CursorPreparer preparer) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFind(collectionName, query, fields, entityClass, preparer);
	}
	
	@Override
	public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
		query.addCriteria(getTenantCriteria());
		return super.find(query, entityClass, collectionName);
	}


	@Override
	protected <T> T doFindAndReplace(String collectionName, Document mappedQuery, Document mappedFields,
			Document mappedSort, com.mongodb.client.model.Collation collation, Class<?> entityType,
			Document replacement, FindAndReplaceOptions options, Class<T> resultType) {
		mappedQuery.putAll(getTenantCriteriaDocument());
		return super.doFindAndReplace(collectionName, mappedQuery, mappedFields, mappedSort, collation, entityType, replacement,
				options, resultType);
	}

	@Override
	protected <T> T doFindOne(String collectionName, Document query, Document fields, Class<T> entityClass) {
		query.putAll(getTenantCriteriaDocument());
		return super.doFindOne(collectionName, query, fields, entityClass);
	}
	
	
	@Override
	protected <T> DeleteResult doRemove(String collectionName, Query query, Class<T> entityClass, boolean multi) {
		query.addCriteria(getTenantCriteria());
		return super.doRemove(collectionName, query, entityClass, multi);
	}
}