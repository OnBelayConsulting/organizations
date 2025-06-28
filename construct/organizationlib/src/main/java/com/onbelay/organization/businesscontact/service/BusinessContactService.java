package com.onbelay.organization.businesscontact.service;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;

import java.util.List;

public interface BusinessContactService {

	public List<BusinessContactSnapshot> findByIds(QuerySelectedPage selectedPage);

	public QuerySelectedPage findBusinessContactIds(DefinedQuery definedQuery);

	public TransactionResult save(BusinessContactSnapshot snapshot);

	public TransactionResult save(List<BusinessContactSnapshot> snapshots);
	
	public BusinessContactSnapshot load(EntityId entityId);

}
