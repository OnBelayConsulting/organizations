/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organization.organization.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshot;

import java.util.List;

public class OrganizationSnapshot extends AbstractSnapshot {

	private OrganizationDetail detail = new OrganizationDetail();

	public OrganizationSnapshot() {
	}

	public OrganizationSnapshot(String errorCode) {
		super(errorCode);
	}

	public OrganizationSnapshot(String errorCode, boolean isPermissionException) {
		super(errorCode, isPermissionException);
	}

	public OrganizationSnapshot(String errorCode, List<String> parameters) {
		super(errorCode, parameters);
	}

	public OrganizationDetail getDetail() {
		return detail;
	}

	public void setDetail(OrganizationDetail detail) {
		this.detail = detail;
	}
	
	public String toString() {
		
		return getEntityId() + " " + detail.toString();
	}

}
