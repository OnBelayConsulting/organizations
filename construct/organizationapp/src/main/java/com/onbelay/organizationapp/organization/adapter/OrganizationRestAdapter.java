package com.onbelay.organizationapp.organization.adapter;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organizationapp.snapshot.OrganizationSnapshotCollection;

import java.util.List;

public interface OrganizationRestAdapter {


    TransactionResult save(OrganizationSnapshot snapshot);

    TransactionResult save(List<OrganizationSnapshot> snapshots);

    OrganizationSnapshotCollection find(String queryText, Integer start, Integer limit);

    OrganizationSnapshot load(EntityId entityId);

    TransactionResult synchronizeOrganizations();
}
