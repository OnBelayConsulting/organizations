package com.onbelay.organizationlib.organization.model;

import com.onbelay.core.entity.persistence.TransactionalSpringTestCase;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.organizationlib.organization.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("com.onbelay")
@TestPropertySource( locations= "classpath:application-integrationtest.properties")
@SpringBootTest

public class OrganizationRepositoryTest extends TransactionalSpringTestCase {

	@Autowired
	private OrganizationRepository organizationRepository;
	private List<Organization> orgs;
	
	@Override
	public void setUp(){
		super.setUp();
		orgs = OrganizationFixture.createOrganizations("mine", 20);
		flush();
	}

	@Test
	public void testCreateOrganization() {
		
		Organization organization =	OrganizationFixture.createOrganization("Jane");
		flush();
		clearCache();
		
		Organization created = organizationRepository.load(organization.generateEntityId());
		
		assertNotNull(created);
		
	}
	
	
	@Test
	public void testFetchIds() {
		
		List<Integer> ids =
				orgs
				.stream()
				.map((o) -> o.getId())
				.collect(
						Collectors.toList());

		System.out.println(ids);

		QuerySelectedPage querySelectedPage = new QuerySelectedPage(ids);

		List<Organization> neworgs = organizationRepository.fetchByIds(querySelectedPage);

		List<Integer> organizationIds = organizationRepository.findOrganizationIds(new DefinedQuery("Organization"));
		
		assertEquals(20, organizationIds.size());
	}


	
}
