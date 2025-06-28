/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organization.organization.repository;

import java.util.List;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organization.organization.model.Organization;

public interface OrganizationRepository {
	public static final String BEAN_NAME = "organizationRepository";
	
	public Organization load(EntityId entityId);

	public Organization findByShortName(String shortName);

	public List<Integer> findOrganizationIds(DefinedQuery definedQuery);
	
	public List<Organization> fetchByIds(QuerySelectedPage querySelectedPage);

}
