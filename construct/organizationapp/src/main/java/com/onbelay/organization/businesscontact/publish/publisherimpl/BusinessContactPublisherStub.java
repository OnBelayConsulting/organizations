package com.onbelay.organization.businesscontact.publish.publisherimpl;

import com.onbelay.organization.businesscontact.publish.publisher.BusinessContactPublisher;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;

import java.util.List;

public class BusinessContactPublisherStub implements BusinessContactPublisher {
    @Override
    public void publish(BusinessContactSnapshot snapshot) {

    }

    @Override
    public void publish(List<BusinessContactSnapshot> snapshots) {

    }
}
