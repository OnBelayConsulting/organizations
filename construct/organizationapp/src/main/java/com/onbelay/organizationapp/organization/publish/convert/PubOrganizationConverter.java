package com.onbelay.organizationapp.organization.publish.convert;

import com.onbelay.organizationapp.organization.publish.snapshot.PubOrganization;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class PubOrganizationConverter {

    public PubOrganization convert(OrganizationSnapshot snapshot) {
        PubOrganization pub = new PubOrganization();

        pub.setEntityState(snapshot.getEntityState());
        pub.setEntityId(snapshot.getEntityId());

        pub.getDetail().setShortName(snapshot.getDetail().getShortName());
        pub.getDetail().setLegalName(snapshot.getDetail().getLegalName());

        return pub;

    }

    public List<PubOrganization> convert(List<OrganizationSnapshot> snapshots) {
        return snapshots
                .stream()
                .map(c-> convert(c))
                .collect(Collectors.toList());
    }

}
