package com.onbelay.organizationapp.organization.publish.publisherimpl;

import com.onbelay.organizationapp.organization.publish.convert.PubOrganizationConverter;
import com.onbelay.organizationapp.organization.publish.publisher.OrganizationPublisher;
import com.onbelay.organizationapp.organization.publish.snapshot.PubOrganization;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;

public class OrganizationPublisherBean implements OrganizationPublisher {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private StreamBridge streamBridge;

    @Value( "${org.organization.queue.name:org.organization.save}")
    private String queueName;

    @Override
    public void publish(OrganizationSnapshot snapshotIn) {
        ArrayList<PubOrganization> snapshots = new ArrayList<>();

        PubOrganizationConverter converter = new PubOrganizationConverter();
        snapshots.add(
                converter.convert(snapshotIn));

        streamBridge.send(queueName, snapshots);
    }



    @Override
    public void publish(List<OrganizationSnapshot> snapshotsIn) {

        PubOrganizationConverter converter = new PubOrganizationConverter();
        List<PubOrganization> snapshots =    converter.convert(snapshotsIn);
        streamBridge.send(queueName, snapshots);
    }

}
