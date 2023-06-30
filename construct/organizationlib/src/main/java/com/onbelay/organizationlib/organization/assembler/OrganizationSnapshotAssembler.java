package com.onbelay.organizationlib.organization.assembler;

import java.util.ArrayList;
import java.util.List;

import com.onbelay.core.entity.assembler.EntityAssembler;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organizationlib.organization.model.Organization;

public class OrganizationSnapshotAssembler extends EntityAssembler {
	
	
	public OrganizationSnapshot assemble(Organization organization) {
		
		OrganizationSnapshot snapshot = new OrganizationSnapshot();
		setEntityAttributes(organization, snapshot);
		
		snapshot.getDetail().copyFrom(organization.getDetail());
		
		return snapshot;
	}
	
	public List<OrganizationSnapshot> assemble(List<Organization> organizations) {
		
		
		ArrayList<OrganizationSnapshot> snapshots = new ArrayList<OrganizationSnapshot>();
		for (Organization o : organizations) {
			snapshots.add(
					assemble(o));
		}
		
		return snapshots;
	}
	

}
