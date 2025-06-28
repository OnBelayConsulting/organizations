package com.onbelay.organization.organization.publish.publisherimpl;

import com.onbelay.organization.organization.publish.convert.OrganizationPublicationConverter;
import com.onbelay.organization.organization.publish.publisher.OrganizationPublisher;
import com.onbelay.organization.organization.publish.snapshot.OrganizationPublication;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;
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
        ArrayList<OrganizationPublication> snapshots = new ArrayList<>();

        OrganizationPublicationConverter converter = new OrganizationPublicationConverter();
        snapshots.add(
                converter.convert(snapshotIn));

        streamBridge.send(queueName, snapshots);
    }



    @Override
    public void publish(List<OrganizationSnapshot> snapshotsIn) {

        OrganizationPublicationConverter converter = new OrganizationPublicationConverter();
        List<OrganizationPublication> snapshots =    converter.convert(snapshotsIn);
        streamBridge.send(queueName, snapshots);
    }

}
