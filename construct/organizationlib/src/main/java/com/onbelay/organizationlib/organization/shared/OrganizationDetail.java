/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organizationlib.organization.shared;

import com.onbelay.core.entity.snapshot.AbstractDetail;
import com.onbelay.core.exception.OBValidationException;
import com.onbelay.organizationlib.organization.enums.OrganizationErrorCode;

import jakarta.persistence.Column;

public class OrganizationDetail extends AbstractDetail {

	private String shortName;
	private boolean isShortNameNull;
	
	private String legalName;
	private boolean isLegalNameNull;
	
	
	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		isShortNameNull = isNull(shortName);
		this.shortName = shortName;
	}
	
	@Column(name = "LEGAL_NAME")
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		isLegalNameNull = isNull(legalName);
		this.legalName = legalName;
	}

	@Override
	public void validate() throws OBValidationException {
		super.validate();
		if (this.shortName == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_SHORT_NAME.getCode());
		if (this.legalName == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_LEGAL_NAME.getCode());
	}

	public void copyFrom(OrganizationDetail copy) {
		
		if (copy.shortName != null || copy.isShortNameNull)
			this.shortName = copy.shortName;
		
		if (copy.legalName != null || copy.isLegalNameNull)
			this.legalName = copy.legalName;
	}
	
	public String toString() {
		return shortName + " : " + legalName;
	}
}
