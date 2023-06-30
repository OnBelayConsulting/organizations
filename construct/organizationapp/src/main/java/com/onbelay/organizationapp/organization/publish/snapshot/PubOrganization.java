package com.onbelay.organizationapp.organization.publish.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class PubOrganization extends AbstractSnapshot {

    private PubOrganizationDetail detail = new PubOrganizationDetail();

    public PubOrganizationDetail getDetail() {
        return detail;
    }

    public void setDetail(PubOrganizationDetail detail) {
        this.detail = detail;
    }
}
