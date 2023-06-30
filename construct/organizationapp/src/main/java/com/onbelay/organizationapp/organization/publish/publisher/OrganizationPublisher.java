package com.onbelay.organizationapp.organization.publish.publisher;

import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;

import java.util.List;

public interface OrganizationPublisher {

    public void publish(OrganizationSnapshot snapshot);

    public void publish(List<OrganizationSnapshot> snapshots);

}
