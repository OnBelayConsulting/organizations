package com.onbelay.organizationapp.organization.adapterimpl;

import com.onbelay.core.controller.BaseRestAdapterBean;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.parsing.DefinedQueryBuilder;
import com.onbelay.core.query.snapshot.DefinedOrderExpression;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organizationapp.organization.adapter.OrganizationRestAdapter;
import com.onbelay.organizationapp.organization.publish.publisher.OrganizationPublisher;
import com.onbelay.organizationapp.snapshot.OrganizationSnapshotCollection;
import com.onbelay.organizationlib.organization.service.OrganizationService;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationRestAdapterBean extends BaseRestAdapterBean implements OrganizationRestAdapter {

    @Autowired
    OrganizationPublisher organizationPublisher;

    @Autowired
    private OrganizationService organizationService;
    @Override
    public TransactionResult save(OrganizationSnapshot snapshot) {
        TransactionResult result = organizationService.save(snapshot);

        OrganizationSnapshot created = organizationService.load(result.getEntityId());

        organizationPublisher.publish(created);

        return result;
    }

    public TransactionResult save(List<OrganizationSnapshot> snapshots) {

        initializeSession();
        TransactionResult result = organizationService.save(snapshots);

        List<OrganizationSnapshot>  saved = organizationService.load(result.getEntityIds());
        organizationPublisher.publish(saved);

        return result;
    }

    @Override
    public OrganizationSnapshotCollection find(
            String queryText,
            Integer start,
            Integer limit) {
        initializeSession();
        DefinedQuery definedQuery;

        if (queryText == null || queryText.equals("default")) {
            definedQuery = new DefinedQuery("Organization");
            definedQuery.getOrderByClause()
                    .addOrderExpression(
                            new DefinedOrderExpression("shortName"));
        } else {
            DefinedQueryBuilder builder = new DefinedQueryBuilder("Organization", queryText);
            definedQuery = builder.build();

            if (definedQuery.getOrderByClause().hasExpressions() == false) {
                definedQuery.getOrderByClause()
                        .addOrderExpression(
                                new DefinedOrderExpression("shortName"));
            }
        }
        QuerySelectedPage allIdsPage = organizationService.findOrganizationIds(definedQuery);
        List<Integer> totalIds = allIdsPage.getIds();

        int toIndex =  start + limit;

        if (toIndex > totalIds.size())
            toIndex = totalIds.size();

        int fromIndex = start;

        if (fromIndex > toIndex)
            return new OrganizationSnapshotCollection(
                    start,
                    limit,
                    totalIds.size());

        List<Integer> selectedIds =  totalIds.subList(fromIndex, toIndex);

        QuerySelectedPage querySelectedPage = new QuerySelectedPage(
                selectedIds,
                definedQuery.getOrderByClause());


        List<OrganizationSnapshot> snapshots = organizationService.findByIds(querySelectedPage);

        return new OrganizationSnapshotCollection(
                start,
                limit,
                totalIds.size(),
                snapshots );

    }

    @Override
    public OrganizationSnapshot load(EntityId entityId) {
        initializeSession();

        return organizationService.load(entityId);
    }
}
