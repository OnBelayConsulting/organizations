package com.onbelay.organization.organization.publish.publisher;

import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;

import java.util.List;

public interface OrganizationPublisher {

    public void publish(OrganizationSnapshot snapshot);

    public void publish(List<OrganizationSnapshot> snapshots);

}
