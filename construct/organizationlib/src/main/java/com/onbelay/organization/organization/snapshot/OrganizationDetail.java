/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organization.organization.snapshot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onbelay.core.entity.snapshot.AbstractDetail;
import com.onbelay.core.exception.OBValidationException;
import com.onbelay.organization.organization.enums.CreditStatusCode;
import com.onbelay.organization.organization.enums.IndustryTypeCode;
import com.onbelay.organization.organization.enums.OrganizationErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

public class OrganizationDetail extends AbstractDetail {

	private String shortName;
	private boolean isShortNameNull;
	
	private String legalName;
	private boolean isLegalNameNull;

	private String industryTypeCodeValue;
	private boolean isIndustryTypeCodeNull;

	private String creditStatusCodeValue;
	private boolean isCreditStatusNull;

	
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

	@JsonIgnore
	@Transient
	public IndustryTypeCode  getIndustryTypeCode() {
		return IndustryTypeCode.lookUp(industryTypeCodeValue);
	}

	public void setIndustryTypeCode(IndustryTypeCode industryTypeCode) {
		setIndustryTypeCodeValue(industryTypeCode.getCode());
	}


	@Column(name = "INDUSTRY_TYPE_CODE")
	public String getIndustryTypeCodeValue() {
		return industryTypeCodeValue;
	}

	public void setIndustryTypeCodeValue(String industryTypeCodeValue) {
		isIndustryTypeCodeNull = isNull(industryTypeCodeValue);
		this.industryTypeCodeValue = industryTypeCodeValue;
	}

	@JsonIgnore
	@Transient
	public CreditStatusCode getCreditStatusCode() {
		return CreditStatusCode.lookUp(creditStatusCodeValue);
	}

	public void setCreditStatusCode(CreditStatusCode creditStatusCode) {
		setCreditStatusCodeValue(creditStatusCode.getCode());
	}

	@Column(name = "CREDIT_STATUS_CODE")
	public String getCreditStatusCodeValue() {
		return creditStatusCodeValue;
	}

	public void setCreditStatusCodeValue(String creditStatusCodeValue) {
		this.creditStatusCodeValue = creditStatusCodeValue;
		isCreditStatusNull = isNull(industryTypeCodeValue);
	}

	@Override
	public void validate() throws OBValidationException {
		super.validate();
		if (this.shortName == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_SHORT_NAME.getCode());

		if (this.legalName == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_LEGAL_NAME.getCode());

		if (this.industryTypeCodeValue == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_INDUSTRY_TYPE.getCode());

		if (this.creditStatusCodeValue == null)
			throw new OBValidationException(OrganizationErrorCode.MISSING_CREDIT_STATUS.getCode());
	}

	public void copyFrom(OrganizationDetail copy) {
		
		if (copy.shortName != null || copy.isShortNameNull)
			this.shortName = copy.shortName;
		
		if (copy.legalName != null || copy.isLegalNameNull)
			this.legalName = copy.legalName;
		
		if (copy.creditStatusCodeValue != null || copy.isCreditStatusNull)
			this.creditStatusCodeValue = copy.creditStatusCodeValue;
		
		if (copy.industryTypeCodeValue != null || copy.isIndustryTypeCodeNull)
			this.industryTypeCodeValue = copy.industryTypeCodeValue;
	}
	
	public String toString() {
		return shortName + " : " + legalName;
	}
}
