package com.onbelay.organization.organization.model;

import java.util.ArrayList;
import java.util.List;

import com.onbelay.organization.organization.enums.CreditStatusCode;
import com.onbelay.organization.organization.enums.IndustryTypeCode;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;

public class OrganizationFixture {
	
	
	public static List<Organization> createOrganizations(String prefixName, int total) {
		
		ArrayList<Organization> organizations = new ArrayList<Organization>();
		for (int i = 0 ; i < total; i++) {
			organizations.add(
					create(prefixName + i));
		}
		return organizations;
	}

	public static Organization create(String name) {

		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		snapshot.getDetail().setShortName(name);
		snapshot.getDetail().setLegalName(name);
		snapshot.getDetail().setIndustryTypeCode(IndustryTypeCode.ENERGY);
		snapshot.getDetail().setCreditStatusCode(CreditStatusCode.DENIED);

		Organization organization = Organization.create(snapshot);
		return organization;
	}
	
	public static Organization create(String name, String legalName) {

		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		snapshot.getDetail().setShortName(name);
		snapshot.getDetail().setLegalName(legalName);
		snapshot.getDetail().setIndustryTypeCode(IndustryTypeCode.ENERGY);
		snapshot.getDetail().setCreditStatusCode(CreditStatusCode.DENIED);

		Organization organization = Organization.create(snapshot);
		return organization;
	}


	public static Organization create(
			String name,
			String legalName,
			CreditStatusCode creditStatusCode,
			IndustryTypeCode industryTypeCode) {

		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		snapshot.getDetail().setShortName(name);
		snapshot.getDetail().setLegalName(legalName);
		snapshot.getDetail().setIndustryTypeCode(industryTypeCode);
		snapshot.getDetail().setCreditStatusCode(creditStatusCode);

		Organization organization = Organization.create(snapshot);
		return organization;
	}


}
