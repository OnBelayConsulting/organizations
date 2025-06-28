package com.onbelay.organization.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organization.test.OrganizationsAppSpringTestCase;
import com.onbelay.organization.organization.assembler.OrganizationSnapshotAssembler;
import com.onbelay.organization.organization.model.Organization;
import com.onbelay.organization.organization.model.OrganizationFixture;
import com.onbelay.organization.organization.service.OrganizationService;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organization.organization.snapshot.OrganizationSnapshotCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OrganizationRestControllerTest extends OrganizationsAppSpringTestCase {
	private static final Logger logger = LogManager.getLogger(OrganizationRestControllerTest.class);
	
	private Organization myOrganization;
	
	@Autowired
	private OrganizationRestController organizationRestController;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public void setUp() {
		super.setUp();

		OrganizationFixture.createOrganizations("y", 30);
		OrganizationFixture.createOrganizations("m", 4);
		myOrganization = OrganizationFixture.create("myName", "LegallyNamed" );
		flush();
	}
	
	
	
	@Test
	public void testPost() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		String jsonString = "{\"detail\":{ \"shortName\": \"fred\", \"legalName\": \"frederick\", \"industryTypeCodeValue\" :\"Retail\", \"creditStatusCodeValue\" : \"Granted\" } }";
		
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
	public void testPutSave() throws Exception {

		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();

		OrganizationSnapshotAssembler assembler = new OrganizationSnapshotAssembler();

		OrganizationSnapshot snapshot = assembler.assemble(myOrganization);

		snapshot.setEntityState(EntityState.MODIFIED);
		snapshot.getDetail().setLegalName("freddie");

		ObjectMapper mapper = new ObjectMapper();
		String jsonString =  mapper.writeValueAsString(List.of(snapshot));

		ResultActions result = mvc.perform(
				put("/api/organizations")
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
	public void testGetWithId() throws Exception {

		MockMvc mvc = MockMvcBuilders.standaloneSetup(organizationRestController)
				.build();


		ResultActions result = mvc.perform(get("/api/organizations/" + myOrganization.getId()));

		MvcResult mvcResult = result.andReturn();

		String jsonStringResponse = mvcResult.getResponse().getContentAsString();

		logger.error("Json: " + jsonStringResponse);

		ObjectMapper mapper = new ObjectMapper();

		OrganizationSnapshot snapshot = mapper.readValue(jsonStringResponse, OrganizationSnapshot.class);


		assertEquals(myOrganization.getDetail().getShortName(), snapshot.getDetail().getShortName());

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
