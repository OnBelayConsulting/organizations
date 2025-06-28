package com.onbelay.organization.businesscontact.adapter;

import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshotCollection;
import com.onbelay.organization.test.OrganizationsAppSpringTestCase;
import com.onbelay.organization.businesscontact.enums.ContactStatusCode;
import com.onbelay.organization.businesscontact.model.BusinessContact;
import com.onbelay.organization.businesscontact.model.BusinessContactFixture;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessContactRestAdapterTest extends OrganizationsAppSpringTestCase {

    @Autowired
    private BusinessContactRestAdapter businessContactRestAdapter;

    private BusinessContact businessContact;

    @Override
    public void setUp() {
        super.setUp();
        businessContact = BusinessContactFixture.createCounterpartyTrader("Anne", "Oak", "anneoak@hap.com");
        flush();
    }

    @Test
    public void fetchCounterpartyContacts() {
        BusinessContactSnapshotCollection collection = businessContactRestAdapter.find(
                "WHERE lastName contains 'Oa' AND firstName = 'Anne' AND contactStatus = 'Pending' AND email eq 'anneoak@hap.com' AND isCounterpartyTrader = true",
                0,
                100);
        assertEquals(1, collection.getCount());
        assertEquals(1, collection.getTotalItems());
        assertEquals(1, collection.getSnapshots().size());

        BusinessContactSnapshot snapshot = collection.getSnapshots().get(0);
        assertEquals("Anne", snapshot.getDetail().getFirstName());

    }

    @Test
    public void  updateContact() {
        BusinessContactSnapshot snapshot = new BusinessContactSnapshot();
        snapshot.getDetail().setFirstName("Cathy");
        snapshot.setEntityState(EntityState.MODIFIED);
        snapshot.setEntityId(businessContact.generateEntityId());
        businessContactRestAdapter.save(snapshot);
        flush();

        BusinessContactSnapshot updated = businessContactRestAdapter.load(businessContact.generateEntityId());
        assertEquals("Cathy", updated.getDetail().getFirstName());

    }

    @Test
    public void saveContacts() {
        ArrayList<BusinessContactSnapshot> contacts = new ArrayList<>();
        BusinessContactSnapshot snapshot = new BusinessContactSnapshot();
        snapshot.getDetail().setFirstName("Anne");
        snapshot.getDetail().setLastName("Oak");
        snapshot.getDetail().setEmail("Oak@HJ.com");
        snapshot.getDetail().setIsCounterpartyTrader(true);
        snapshot.getDetail().setIsCompanyTrader(false);
        snapshot.getDetail().setIsAdministrator(false);
        snapshot.getDetail().setContactStatusCode(ContactStatusCode.SUSPENDED);
        contacts.add(snapshot);
        snapshot = new BusinessContactSnapshot();
        snapshot.getDetail().setFirstName("Anne");
        snapshot.getDetail().setLastName("Oak2");
        snapshot.getDetail().setEmail("Oak2@HJ.com");
        snapshot.getDetail().setIsCounterpartyTrader(true);
        snapshot.getDetail().setIsCompanyTrader(false);
        snapshot.getDetail().setIsAdministrator(false);
        snapshot.getDetail().setIsCounterpartyTrader(true);
        snapshot.getDetail().setContactStatusCode(ContactStatusCode.SUSPENDED);
        contacts.add(snapshot);
        TransactionResult result = businessContactRestAdapter.save(contacts);
        flush();

        assertEquals(2, result.getIds().size());
        BusinessContactSnapshotCollection collection = businessContactRestAdapter.find(
                "WHERE ",
                0,
                100);
        assertEquals(3, collection.getCount());
    }
}
