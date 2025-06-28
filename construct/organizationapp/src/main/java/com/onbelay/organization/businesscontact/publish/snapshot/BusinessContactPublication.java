package com.onbelay.organization.businesscontact.publish.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class BusinessContactPublication extends AbstractSnapshot {

    private BusinessContactPublicationDetail detail = new BusinessContactPublicationDetail();

    public BusinessContactPublicationDetail getDetail() {
        return detail;
    }

    public void setDetail(BusinessContactPublicationDetail detail) {
        this.detail = detail;
    }
}
