package com.onbelay.organizationapp.organization.adapter;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organizationapp.snapshot.OrganizationSnapshotCollection;

public interface OrganizationRestAdapter {


    TransactionResult save(OrganizationSnapshot snapshot);

    OrganizationSnapshotCollection find(String queryText, Integer start, Integer limit);

    OrganizationSnapshot load(EntityId entityId);
}
