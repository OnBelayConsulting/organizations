package com.onbelay.organization.organization.service;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;

import java.util.List;

public interface OrganizationService {

	public List<OrganizationSnapshot> findByIds(QuerySelectedPage selectedPage);

	public QuerySelectedPage findOrganizationIds(DefinedQuery definedQuery);

	public TransactionResult save(OrganizationSnapshot snapshot);

	public TransactionResult save(List<OrganizationSnapshot> snapshots);
	
	public OrganizationSnapshot load(EntityId entityId);

}
