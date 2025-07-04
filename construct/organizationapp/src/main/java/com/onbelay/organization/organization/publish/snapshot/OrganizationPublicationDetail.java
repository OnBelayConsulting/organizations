package com.onbelay.organization.organization.publish.snapshot;

import com.onbelay.core.entity.snapshot.AbstractDetail;

public class OrganizationPublicationDetail extends AbstractDetail {

    private String shortName;
    private String legalName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
}
