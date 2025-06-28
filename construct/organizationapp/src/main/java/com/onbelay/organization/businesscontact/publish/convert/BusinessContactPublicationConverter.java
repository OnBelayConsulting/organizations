package com.onbelay.organization.businesscontact.publish.convert;


import com.onbelay.organization.businesscontact.publish.snapshot.BusinessContactPublication;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessContactPublicationConverter {

    public BusinessContactPublication convert(BusinessContactSnapshot snapshot) {
        BusinessContactPublication publication = new BusinessContactPublication();

        publication.setEntityState(snapshot.getEntityState());
        publication.setEntityId(snapshot.getEntityId());

        publication.getDetail().setFirstName(snapshot.getDetail().getFirstName());
        publication.getDetail().setLastName(snapshot.getDetail().getLastName());
        publication.getDetail().setEmail(snapshot.getDetail().getEmail());

        publication.getDetail().setContactStatusCodeValue(snapshot.getDetail().getContactStatusCodeValue());

        publication.getDetail().setIsCompanyTrader(snapshot.getDetail().getIsCompanyTrader());
        publication.getDetail().setIsCounterpartyTrader(snapshot.getDetail().getIsCounterpartyTrader());
        publication.getDetail().setIsAdministrator(snapshot.getDetail().getIsAdministrator());



        return publication;

    }

    public List<BusinessContactPublication> convert(List<BusinessContactSnapshot> snapshots) {
        return snapshots
                .stream()
                .map(c-> convert(c))
                .collect(Collectors.toList());
    }

}
