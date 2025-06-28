package com.onbelay.organization.businesscontact.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshotCollection;

import java.util.List;


public class BusinessContactSnapshotCollection extends AbstractSnapshotCollection<BusinessContactSnapshot> {

	public static final String ITEM_TYPE = "businessContacts";

	public BusinessContactSnapshotCollection() {
		super(ITEM_TYPE);
	}


	public BusinessContactSnapshotCollection(
			String errorMessage) {

		super(
				ITEM_TYPE,
				errorMessage);
	}


	public BusinessContactSnapshotCollection(
			int start,
			int limit,
			int totalItems) {

		super(
				ITEM_TYPE,
				start,
				limit,
				totalItems);
	}

	public BusinessContactSnapshotCollection(
			int start,
			int limit,
			int totalItems,
			List<BusinessContactSnapshot> snapshots ) {

		super(
				ITEM_TYPE,
				start,
				limit,
				totalItems,
				snapshots);
	}
	

}
