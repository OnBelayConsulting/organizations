package com.onbelay.organizationapp.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshotCollection;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;

import java.util.List;


public class OrganizationSnapshotCollection extends AbstractSnapshotCollection<OrganizationSnapshot> {

	public static final String ITEM_TYPE = "organizations";
	
	public OrganizationSnapshotCollection() {
		super(ITEM_TYPE);
	}
	

	public OrganizationSnapshotCollection(
			String errorMessage) {

		super(
				ITEM_TYPE,
				errorMessage);
	}


	public OrganizationSnapshotCollection(
			int start,
			int limit,
			int totalItems) {

		super(
				ITEM_TYPE,
				start,
				limit,
				totalItems);
	}
	
	public OrganizationSnapshotCollection(
			int start,
			int limit,
			int totalItems,
			List<OrganizationSnapshot> snapshots ) {

		super(
				ITEM_TYPE,
				start,
				limit,
				totalItems,
				snapshots);
	}
	

}
