package com.onbelay.organizationlib.organization.model;

import java.util.ArrayList;
import java.util.List;

import com.onbelay.organizationlib.organization.model.Organization;
import com.onbelay.organizationlib.organization.shared.OrganizationDetail;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;

public class OrganizationFixture {
	
	
	public static List<Organization> createOrganizations(String prefixName, int total) {
		
		ArrayList<Organization> organizations = new ArrayList<Organization>();
		for (int i = 0 ; i < total; i++) {
			organizations.add(
					createOrganization(prefixName + i));
		}
		return organizations;
	}

	public static Organization createOrganization(String name) {

		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		snapshot.getDetail().setShortName(name);
		snapshot.getDetail().setLegalName(name);
		
		Organization organization = Organization.create(snapshot);
		return organization;
	}
	
	public static Organization createOrganization(String name, String legalName) {

		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		snapshot.getDetail().setShortName(name);
		snapshot.getDetail().setLegalName(legalName);

		Organization organization = Organization.create(snapshot);
		return organization;
	}
	
}
