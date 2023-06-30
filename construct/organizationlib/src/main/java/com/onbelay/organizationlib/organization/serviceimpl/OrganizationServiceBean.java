package com.onbelay.organizationlib.organization.serviceimpl;

import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.serviceimpl.BaseDomainService;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organizationlib.organization.assembler.OrganizationSnapshotAssembler;
import com.onbelay.organizationlib.organization.enums.OrganizationErrorCode;
import com.onbelay.organizationlib.organization.model.Organization;
import com.onbelay.organizationlib.organization.repository.OrganizationRepository;
import com.onbelay.organizationlib.organization.service.OrganizationService;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "organizationService")
@Transactional
public class OrganizationServiceBean extends BaseDomainService implements OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public List<OrganizationSnapshot> findByIds(QuerySelectedPage selectedPage) {
		List<Organization> organizations = organizationRepository.fetchByIds(selectedPage);
		OrganizationSnapshotAssembler assembler = new OrganizationSnapshotAssembler();
		return assembler.assemble(organizations);
	}

	@Override
	public QuerySelectedPage findOrganizationIds(DefinedQuery definedQuery) {

		return new QuerySelectedPage(
			organizationRepository.findOrganizationIds(definedQuery),
			definedQuery.getOrderByClause());
	}

	@Override
	public OrganizationSnapshot load(EntityId entityId) {

		Organization organization = organizationRepository.load(entityId);
		if (organization == null)
			throw new OBRuntimeException(OrganizationErrorCode.MISSING_ORGANIZATION.getCode());
		
		OrganizationSnapshotAssembler assembler = new OrganizationSnapshotAssembler();
		return assembler.assemble(organization);
	}

	@Override
	public List<OrganizationSnapshot> load(List<EntityId> ids) {
		QuerySelectedPage selectedPage = new QuerySelectedPage(
				ids
						.stream()
						.map(c-> c.getId())
						.collect(Collectors.toList()));

		List<Organization> organizations = organizationRepository.fetchByIds(selectedPage);
		OrganizationSnapshotAssembler assembler = new OrganizationSnapshotAssembler();
		return assembler.assemble(organizations);
	}

	@Override
	public TransactionResult save(OrganizationSnapshot snapshot) {

		Organization organization;
		
		if (snapshot.getEntityState() == EntityState.NEW) {
			
			organization = Organization.create(snapshot);

		} else {
			organization = organizationRepository.load(snapshot.getEntityId());
			if (organization == null)
				throw new OBRuntimeException(OrganizationErrorCode.MISSING_ORGANIZATION.getCode());
			organization.updateWith(snapshot);
		}
		return new TransactionResult(organization.generateEntityId());
	}

	@Override
	public TransactionResult save(List<OrganizationSnapshot> snapshots) {

		TransactionResult result = new TransactionResult();
		
		for (OrganizationSnapshot snapshot : snapshots) {
			Organization organization;
			
			if (snapshot.getEntityState() == EntityState.NEW) {
				
				organization = Organization.create(snapshot);

			} else {
				organization = organizationRepository.load(snapshot.getEntityId());
				organization.updateWith(snapshot);
			}
			result.addEntityId(organization.generateEntityId());
			
		}
		return result;
	}
	
}
