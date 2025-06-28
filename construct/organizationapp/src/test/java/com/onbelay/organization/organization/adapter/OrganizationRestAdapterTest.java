package com.onbelay.organization.organization.adapter;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshotCollection;
import com.onbelay.organization.test.OrganizationsAppSpringTestCase;
import com.onbelay.organization.organization.enums.CreditStatusCode;
import com.onbelay.organization.organization.enums.IndustryTypeCode;
import com.onbelay.organization.organization.model.Organization;
import com.onbelay.organization.organization.model.OrganizationFixture;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrganizationRestAdapterTest extends OrganizationsAppSpringTestCase {

    @Autowired
    private OrganizationRestAdapter organizationRestAdapter;

    private Organization myOrganization;

    @Override
    public void setUp() {
        super.setUp();

        OrganizationFixture.createOrganizations("y", 30);
        OrganizationFixture.createOrganizations("m", 4);
        
        myOrganization = OrganizationFixture.create(
                "LakeView", 
                "LakeView Ltd", 
                CreditStatusCode.PENDING, 
                IndustryTypeCode.FINANCIAL );
        flush();
    }


    @Test
    public void findOrganizationByEntityId() {

        OrganizationSnapshot snapshot = organizationRestAdapter.load(new EntityId("LakeView"));
        assertNotNull(snapshot);


    }



    @Test
    public void findOrganization() {

        OrganizationSnapshotCollection collection = organizationRestAdapter.find(
                "WHERE shortName = 'LakeView' AND legalName = 'LakeView Ltd' AND creditStatus = 'Pending' AND industryType = 'Financial' ",
                0,
                10);

        assertEquals(1, collection.getCount());

    }

}
