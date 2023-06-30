package com.onbelay.organizationapp.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.persistence.TransactionalSpringTestCase;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organizationlib.organization.assembler.OrganizationSnapshotAssembler;
import com.onbelay.organizationlib.organization.model.Organization;
import com.onbelay.organizationlib.organization.model.OrganizationFixture;
import com.onbelay.organizationlib.organization.service.OrganizationService;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organizationapp.snapshot.OrganizationSnapshotCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WithMockUser
@ComponentScan("com.onbelay")
@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource( locations="classpath:application-integrationtest.properties")
public class OrganizationRestControllerTest extends TransactionalSpringTestCase {
	private static final Logger logger = LogManager.getLogger(OrganizationRestControllerTest.class);
	
	private Organization myOrganization;
	
	@Autowired
	private OrganizationRestController organizationRestController;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public void beforeRun() throws Throwable {
		super.beforeRun();

		OrganizationFixture.createOrganizations("y", 30);
		OrganizationFixture.createOrganizations("m", 4);
		myOrganization = OrganizationFixture.createOrganization("myName", "LegallyNamed" );
		flush();
	}
	
	
	
	@Test
	public void testPost() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		String jsonString = "{\"detail\":{ \"shortName\": \"fred\", \"legalName\": \"frederick\" } }";
		
		ResultActions result = mvc.perform(
				post("/api/organizations")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);
	
		ObjectMapper mapper = new ObjectMapper();
		
		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		OrganizationSnapshot created = organizationService.load(transactionResult.getEntityId());
		assertEquals("fred", created.getDetail().getShortName());
		assertEquals("frederick", created.getDetail().getLegalName());

	}

	@Test
	public void testUpdate() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		OrganizationSnapshotAssembler assembler = new OrganizationSnapshotAssembler();
		
		OrganizationSnapshot snapshot = assembler.assemble(myOrganization);
		
		snapshot.setEntityState(EntityState.MODIFIED);
		snapshot.getDetail().setLegalName("freddie");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString =  mapper.writeValueAsString(snapshot);
		
		ResultActions result = mvc.perform(
				post("/api/organizations")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);

		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		OrganizationSnapshot created = organizationService.load(transactionResult.getEntityId());
		assertEquals(myOrganization.getDetail().getShortName(), created.getDetail().getShortName());
		assertEquals("freddie", created.getDetail().getLegalName());
	}
	
	@Test
	public void testPostUpdateWithJson() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		
		String jsonString =  "{\"entityState\":\"MODIFIED\", \"entityId\" : { \"id\" :  "
				+ myOrganization.getId()
				+ "},\"detail\":{\"shortName\":\"john\"}}";
		
		ResultActions result = mvc.perform(
				post("/api/organizations")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);
		
		ObjectMapper mapper = new ObjectMapper();
		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		OrganizationSnapshot created = organizationService.load(transactionResult.getEntityId());
		assertEquals("john", created.getDetail().getShortName());

	}
	

	
	@Test
	public void testGet() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		
		ResultActions result = mvc.perform(get("/api/organizations"));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error("Json: " + jsonStringResponse);
		
		ObjectMapper mapper = new ObjectMapper();
		
		OrganizationSnapshotCollection collection = mapper.readValue(jsonStringResponse, OrganizationSnapshotCollection.class);
		
		assertEquals(35, collection.getSnapshots().size());
		
		assertEquals(35, collection.getCount());

		assertEquals(35, collection.getTotalItems());
		
	}

	
	@Test
	public void testGetWithQuery() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		
		String queryText = "WHERE shortName = 'm1'";
		
		ResultActions result = mvc.perform(get("/api/organizations?query=WHERE shortName like 'm%'"));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		
		OrganizationSnapshotCollection collection = mapper.readValue(jsonStringResponse, OrganizationSnapshotCollection.class);
		
		assertEquals(5, collection.getSnapshots().size());
		
		assertEquals(5, collection.getCount());

		assertEquals(5, collection.getTotalItems());
		
	}
	
}
