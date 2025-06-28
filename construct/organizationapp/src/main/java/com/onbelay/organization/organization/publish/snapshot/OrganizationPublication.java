package com.onbelay.organization.organization.publish.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class OrganizationPublication extends AbstractSnapshot {

    private OrganizationPublicationDetail detail = new OrganizationPublicationDetail();

    public OrganizationPublicationDetail getDetail() {
        return detail;
    }

    public void setDetail(OrganizationPublicationDetail detail) {
        this.detail = detail;
    }
}
