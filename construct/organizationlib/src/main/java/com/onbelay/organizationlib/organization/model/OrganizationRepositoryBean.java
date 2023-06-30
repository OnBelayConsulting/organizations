/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organizationlib.organization.model;

import java.util.List;

import javax.transaction.Transactional;

import com.onbelay.core.enums.CoreTransactionErrorCode;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.organizationlib.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onbelay.core.entity.repository.BaseRepository;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;

@Repository (value="organizationRepository")
@Transactional
public class OrganizationRepositoryBean extends BaseRepository<Organization> implements OrganizationRepository {
	public static final String FETCH_ALL_ORGANIZATIONS = "OrganizationRepository.FETCH_ALL_ORGANIZATIONS";
	public static final String FIND_BY_SHORT_NAME = "OrganizationRepository.FIND_BY_SHORT_NAME";

	@Autowired
	private OrganizationColumnDefinitions organizationColumnDefinitions;
	
	public Organization load(EntityId entityId) {

		if (entityId == null)
			throw new OBRuntimeException(CoreTransactionErrorCode.INVALID_ENTITY_ID.getCode());

		if (entityId.isNull())
			return null;

		if (entityId.isInvalid())
			throw new OBRuntimeException(CoreTransactionErrorCode.INVALID_ENTITY_ID.getCode());

		if (entityId.isSet())
			return (Organization) find(Organization.class, entityId.getId());
		else if (entityId.getCode() != null)
			return  findByShortName(entityId.getCode());
		else
			return null;
	}
	

	
	@Override
	public List<Integer> findOrganizationIds(DefinedQuery definedQuery) {
		
		return executeDefinedQueryForIds(
				new OrganizationColumnDefinitions(), 
				definedQuery);
	
	}

	@Override
	public Organization findByShortName(String shortName) {
		return executeSingleResultQuery(FIND_BY_SHORT_NAME, "shortName", shortName);
	}

	@Override
	public List<Organization> fetchByIds(QuerySelectedPage querySelectedPage) {
		
		return fetchEntitiesById(
				organizationColumnDefinitions,
				"Organization",
				querySelectedPage);
	}



}
