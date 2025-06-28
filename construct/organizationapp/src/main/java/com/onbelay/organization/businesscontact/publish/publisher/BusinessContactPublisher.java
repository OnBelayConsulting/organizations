package com.onbelay.organization.businesscontact.publish.publisher;

import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;

import java.util.List;

public interface BusinessContactPublisher {

    public void publish(BusinessContactSnapshot snapshot);

    public void publish(List<BusinessContactSnapshot> snapshots);

}
