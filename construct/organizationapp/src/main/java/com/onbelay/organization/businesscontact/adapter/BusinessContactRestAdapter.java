package com.onbelay.organization.businesscontact.adapter;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshotCollection;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;

import java.util.List;

public interface BusinessContactRestAdapter {


    TransactionResult save(BusinessContactSnapshot snapshot);

    TransactionResult save(List<BusinessContactSnapshot> snapshots);

    BusinessContactSnapshotCollection find(String queryText, Integer start, Integer limit);

    BusinessContactSnapshot load(EntityId entityId);

    TransactionResult synchronizeBusinessContacts();
}
