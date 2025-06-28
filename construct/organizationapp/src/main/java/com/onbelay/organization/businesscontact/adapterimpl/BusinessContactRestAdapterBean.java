package com.onbelay.organization.businesscontact.adapterimpl;

import com.onbelay.core.controller.BaseRestAdapterBean;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.parsing.DefinedQueryBuilder;
import com.onbelay.core.query.snapshot.DefinedOrderExpression;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organization.businesscontact.adapter.BusinessContactRestAdapter;
import com.onbelay.organization.businesscontact.publish.publisher.BusinessContactPublisher;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshotCollection;
import com.onbelay.organization.businesscontact.service.BusinessContactService;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessContactRestAdapterBean extends BaseRestAdapterBean implements BusinessContactRestAdapter {

    @Autowired
    BusinessContactPublisher businessContactPublisher;

    @Autowired
    private BusinessContactService businessContactService;

    @Override
    public TransactionResult save(BusinessContactSnapshot snapshot) {
        super.initializeSession();
        TransactionResult result = businessContactService.save(snapshot);

        BusinessContactSnapshot created = businessContactService.load(result.getEntityId());

        businessContactPublisher.publish(created);

        return result;
    }

    @Override
    public TransactionResult synchronizeBusinessContacts() {

        QuerySelectedPage selectedPage = businessContactService.findBusinessContactIds(new DefinedQuery("BusinessContact"));
        List<BusinessContactSnapshot> existing = businessContactService.findByIds(selectedPage);
        businessContactPublisher.publish(existing);
        return new TransactionResult();
    }

    @Override
    public TransactionResult save(List<BusinessContactSnapshot> snapshots) {
        super.initializeSession();

        initializeSession();
        TransactionResult result = businessContactService.save(snapshots);

        List<BusinessContactSnapshot>  saved = businessContactService.findByIds(new QuerySelectedPage(result.getIds()));
        businessContactPublisher.publish(saved);

        return result;
    }

    @Override
    public BusinessContactSnapshotCollection find(
            String queryText,
            Integer start,
            Integer limit) {
        initializeSession();
        DefinedQuery definedQuery;

        if (queryText == null || queryText.equals("default")) {
            definedQuery = new DefinedQuery("BusinessContact");
            definedQuery.getOrderByClause()
                    .addOrderExpression(
                            new DefinedOrderExpression("lastName"));
        } else {
            DefinedQueryBuilder builder = new DefinedQueryBuilder("BusinessContact", queryText);
            definedQuery = builder.build();

            if (definedQuery.getOrderByClause().hasExpressions() == false) {
                definedQuery.getOrderByClause()
                        .addOrderExpression(
                                new DefinedOrderExpression("lastName"));
            }
        }
        QuerySelectedPage allIdsPage = businessContactService.findBusinessContactIds(definedQuery);
        List<Integer> totalIds = allIdsPage.getIds();

        int toIndex =  start + limit;

        if (toIndex > totalIds.size())
            toIndex = totalIds.size();

        int fromIndex = start;

        if (fromIndex > toIndex)
            return new BusinessContactSnapshotCollection(
                    start,
                    limit,
                    totalIds.size());

        List<Integer> selectedIds =  totalIds.subList(fromIndex, toIndex);

        QuerySelectedPage querySelectedPage = new QuerySelectedPage(
                selectedIds,
                definedQuery.getOrderByClause());


        List<BusinessContactSnapshot> snapshots = businessContactService.findByIds(querySelectedPage);

        return new BusinessContactSnapshotCollection(
                start,
                limit,
                totalIds.size(),
                snapshots );

    }

    @Override
    public BusinessContactSnapshot load(EntityId entityId) {
        initializeSession();

        return businessContactService.load(entityId);
    }
}
