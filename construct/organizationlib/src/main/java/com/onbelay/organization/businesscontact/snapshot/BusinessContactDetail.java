/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organization.businesscontact.snapshot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onbelay.core.entity.snapshot.AbstractDetail;
import com.onbelay.core.exception.OBValidationException;
import com.onbelay.organization.businesscontact.enums.BusinessContactErrorCode;
import com.onbelay.organization.businesscontact.enums.ContactStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Transient;
import org.hibernate.type.YesNoConverter;

public class BusinessContactDetail extends AbstractDetail {

	private String firstName;
	private boolean isFirstNameNull;
	
	private String lastName;
	private boolean isLastNameNull;

	private String email;
	private boolean isEmailNull;

	private String phone;
	private boolean isPhoneNull;

	private String contactStatusCodeValue;
	private boolean isContactStatusNull;

	private Boolean isCompanyTrader;
	private Boolean isCounterpartyTrader;
	private Boolean isAdministrator;

	
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		isFirstNameNull = isNull(firstName);
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		isLastNameNull = isNull(lastName);
		this.lastName = lastName;
	}


	@Column(name = "CONTACT_EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		isEmailNull = isNull(email);
		this.email = email;
	}

	@Column(name = "CONTACT_PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		isPhoneNull = isNull(phone);
		this.phone = phone;
	}

	@Transient
	@JsonIgnore
	public ContactStatusCode getContactStatusCode() {
		return ContactStatusCode.lookUp(contactStatusCodeValue);
	}

	public void setContactStatusCode(ContactStatusCode code) {
		this.setContactStatusCodeValue(code.getCode());
	}

	@Column(name = "CONTACT_STATUS_CODE")
	public String getContactStatusCodeValue() {
		return contactStatusCodeValue;
	}

	public void setContactStatusCodeValue(String contactStatusCodeValue) {
		this.contactStatusCodeValue = contactStatusCodeValue;
		isContactStatusNull = isNull(contactStatusCodeValue);
	}

	@Column(name = "IS_COMPANY_TRADER")
	@Convert(
			converter = YesNoConverter.class
	)
	public Boolean getIsCompanyTrader() {
		return isCompanyTrader;
	}

	public void setIsCompanyTrader(Boolean companyTrader) {
		isCompanyTrader = companyTrader;
	}

	@Column(name = "IS_COUNTERPARTY_TRADER")
	@Convert(
			converter = YesNoConverter.class
	)
	public Boolean getIsCounterpartyTrader() {
		return isCounterpartyTrader;
	}

	public void setIsCounterpartyTrader(Boolean counterpartyTrader) {
		isCounterpartyTrader = counterpartyTrader;
	}

	@Column(name = "IS_ADMINISTRATOR")
	@Convert(
			converter = YesNoConverter.class
	)
	public Boolean getIsAdministrator() {
		return isAdministrator;
	}

	public void setIsAdministrator(Boolean administrator) {
		isAdministrator = administrator;
	}

	@Override
	public void validate() throws OBValidationException {
		super.validate();
		if (this.firstName == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_FIRST_NAME.getCode());

		if (this.lastName == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_LAST_NAME.getCode());

		if (this.email == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_EMAIL.getCode());

		if (this.isCompanyTrader == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_IS_COMPANY_TRADER.getCode());

		if (this.isCounterpartyTrader == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_IS_COUNTERPARTY_TRADER.getCode());

		if (this.isAdministrator == null)
			throw new OBValidationException(BusinessContactErrorCode.MISSING_IS_ADMINISTRATOR.getCode());
	}

	public void copyFrom(BusinessContactDetail copy) {
		
		if (copy.firstName != null || copy.isFirstNameNull)
			this.firstName = copy.firstName;
		
		if (copy.lastName != null || copy.isLastNameNull)
			this.lastName = copy.lastName;

		if (copy.contactStatusCodeValue != null || copy.isContactStatusNull)
			this.contactStatusCodeValue = copy.contactStatusCodeValue;

		if (copy.email != null || copy.isEmailNull)
			this.email = copy.email;

		if (copy.phone != null || copy.isPhoneNull)
			this.phone = copy.phone;

		if (copy.isAdministrator != null)
			this.isAdministrator = copy.isAdministrator;

		if (copy.isCompanyTrader != null)
			this.isCompanyTrader = copy.isCompanyTrader;

		if (copy.isCounterpartyTrader != null)
			this.isCounterpartyTrader = copy.isCounterpartyTrader;
	}
	
	public String toString() {
		return firstName + " : " + lastName;
	}
}
