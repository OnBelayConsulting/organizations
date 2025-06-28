package com.onbelay.organization.organization.publish.convert;

import com.onbelay.organization.organization.publish.snapshot.OrganizationPublication;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class OrganizationPublicationConverter {

    public OrganizationPublication convert(OrganizationSnapshot snapshot) {
        OrganizationPublication pub = new OrganizationPublication();

        pub.setEntityState(snapshot.getEntityState());
        pub.setEntityId(snapshot.getEntityId());

        pub.getDetail().setShortName(snapshot.getDetail().getShortName());
        pub.getDetail().setLegalName(snapshot.getDetail().getLegalName());

        return pub;

    }

    public List<OrganizationPublication> convert(List<OrganizationSnapshot> snapshots) {
        return snapshots
                .stream()
                .map(c-> convert(c))
                .collect(Collectors.toList());
    }

}
