package com.onbelay.organization.businesscontact.publish.publisherimpl;

import com.onbelay.organization.businesscontact.publish.convert.BusinessContactPublicationConverter;
import com.onbelay.organization.businesscontact.publish.publisher.BusinessContactPublisher;
import com.onbelay.organization.businesscontact.publish.snapshot.BusinessContactPublication;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;

public class BusinessContactPublisherBean implements BusinessContactPublisher {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private StreamBridge streamBridge;

    private String queueName = "org.businessContacts.save";

    @Override
    public void publish(BusinessContactSnapshot snapshotIn) {
        ArrayList<BusinessContactPublication> snapshots = new ArrayList<>();

        BusinessContactPublicationConverter converter = new BusinessContactPublicationConverter();
        snapshots.add(
                converter.convert(snapshotIn));

        streamBridge.send(queueName, snapshots);
    }



    @Override
    public void publish(List<BusinessContactSnapshot> snapshotsIn) {

        BusinessContactPublicationConverter converter = new BusinessContactPublicationConverter();
        List<BusinessContactPublication> snapshots =    converter.convert(snapshotsIn);
        streamBridge.send(queueName, snapshots);
    }

}
