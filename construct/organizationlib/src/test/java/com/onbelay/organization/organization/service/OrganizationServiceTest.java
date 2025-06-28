package com.onbelay.organization.organization.service;

import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organization.organization.enums.CreditStatusCode;
import com.onbelay.organization.organization.enums.IndustryTypeCode;
import com.onbelay.organization.organization.model.Organization;
import com.onbelay.organization.organization.repository.OrganizationRepository;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organization.test.OrganizationSpringTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrganizationServiceTest  extends OrganizationSpringTestCase {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void createOrganization() {
        OrganizationSnapshot snapshot = new OrganizationSnapshot();
        snapshot.getDetail().setIndustryTypeCode(IndustryTypeCode.FINANCIAL);
        snapshot.getDetail().setCreditStatusCode(CreditStatusCode.DENIED);
        snapshot.getDetail().setLegalName("Legal Name");
        snapshot.getDetail().setShortName("Short Name");

        TransactionResult result = organizationService.save(snapshot);
        flush();
        Organization saved = organizationRepository.load(result.getEntityId());
        assertNotNull(saved);
    }

    @Test
    public void updateOrganization() {
        OrganizationSnapshot snapshot = new OrganizationSnapshot();
        snapshot.getDetail().setIndustryTypeCode(IndustryTypeCode.FINANCIAL);
        snapshot.getDetail().setCreditStatusCode(CreditStatusCode.DENIED);
        snapshot.getDetail().setLegalName("Legal Name");
        snapshot.getDetail().setShortName("Short Name");

        TransactionResult result = organizationService.save(snapshot);
        flush();
        Organization saved = organizationRepository.load(result.getEntityId());
        assertNotNull(saved);
        clearCache();
        snapshot = new OrganizationSnapshot();
        snapshot.setEntityId(result.getEntityId());
        snapshot.setEntityState(EntityState.MODIFIED);
        snapshot.getDetail().setIndustryTypeCode(IndustryTypeCode.ENERGY);
        organizationService.save(snapshot);
        flush();
        clearCache();
        Organization updated = organizationRepository.load(result.getEntityId());
        assertNotNull(updated);
        assertEquals(IndustryTypeCode.ENERGY, updated.getDetail().getIndustryTypeCode());

    }
}
