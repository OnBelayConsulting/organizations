package com.onbelay.organization.businesscontact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshotCollection;
import com.onbelay.organization.test.OrganizationsAppSpringTestCase;
import com.onbelay.organization.businesscontact.assembler.BusinessContactSnapshotAssembler;
import com.onbelay.organization.businesscontact.model.BusinessContact;
import com.onbelay.organization.businesscontact.model.BusinessContactFixture;
import com.onbelay.organization.businesscontact.service.BusinessContactService;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;
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

public class BusinessContactRestControllerTest extends OrganizationsAppSpringTestCase {
	private static final Logger logger = LogManager.getLogger(BusinessContactRestControllerTest.class);
	
	private BusinessContact myBusinessContact;
	
	@Autowired
	private BusinessContactRestController businessContactRestController;

	@Autowired
	private BusinessContactService businessContactService;

	@Override
	public void setUp() {
		super.setUp();

		BusinessContactFixture.createCounterpartyTrader("Kyle", "file", "file@jo.com");
		BusinessContactFixture.createCounterpartyTrader("Sue", "Loo", "loo@loo.com");
		myBusinessContact = BusinessContactFixture.createCounterpartyTrader("myName", "Lastt", "llast@hj.com" );
		flush();
	}
	
	
	
	@Test
	public void testPost() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		String jsonString = "{\"detail\":{ \"firstName\": \"fred\", \"lastName\": \"frederick\", \"email\" : \"jh.com\", \"contactStatusCodeValue\" :\"Pending\", \"isCounterpartyTrader\" : true, \"isCompanyTrader\" : true, \"isAdministrator\" : true  } }";
		
		ResultActions result = mvc.perform(
				post("/api/businessContacts")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);
	
		ObjectMapper mapper = new ObjectMapper();
		
		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		BusinessContactSnapshot created = businessContactService.load(transactionResult.getEntityId());
		assertEquals("fred", created.getDetail().getFirstName());
		assertEquals("frederick", created.getDetail().getLastName());

	}

	@Test
	public void testUpdate() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		BusinessContactSnapshotAssembler assembler = new BusinessContactSnapshotAssembler();
		
		BusinessContactSnapshot snapshot = assembler.assemble(myBusinessContact);
		
		snapshot.setEntityState(EntityState.MODIFIED);
		snapshot.getDetail().setLastName("freddie");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString =  mapper.writeValueAsString(snapshot);
		
		ResultActions result = mvc.perform(
				post("/api/businessContacts")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);

		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		BusinessContactSnapshot created = businessContactService.load(transactionResult.getEntityId());
		assertEquals(myBusinessContact.getDetail().getFirstName(), created.getDetail().getFirstName());
		assertEquals("freddie", created.getDetail().getLastName());
	}


	@Test
	public void testPutSave() throws Exception {

		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		BusinessContactSnapshotAssembler assembler = new BusinessContactSnapshotAssembler();

		BusinessContactSnapshot snapshot = assembler.assemble(myBusinessContact);

		snapshot.setEntityState(EntityState.MODIFIED);
		snapshot.getDetail().setLastName("freddie");

		ObjectMapper mapper = new ObjectMapper();
		String jsonString =  mapper.writeValueAsString(List.of(snapshot));

		ResultActions result = mvc.perform(
				put("/api/businessContacts")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonString));

		MvcResult mvcResult = result.andReturn();

		String jsonStringResponse = mvcResult.getResponse().getContentAsString();

		logger.error(jsonStringResponse);

		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		BusinessContactSnapshot created = businessContactService.load(transactionResult.getEntityId());
		assertEquals(myBusinessContact.getDetail().getFirstName(), created.getDetail().getFirstName());
		assertEquals("freddie", created.getDetail().getLastName());
	}


	@Test
	public void testPostUpdateWithJson() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		
		String jsonString =  "{\"entityState\":\"MODIFIED\", \"entityId\" : { \"id\" :  "
				+ myBusinessContact.getId()
				+ "},\"detail\":{\"firstName\":\"john\"}}";
		
		ResultActions result = mvc.perform(
				post("/api/businessContacts")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error(jsonStringResponse);
		
		ObjectMapper mapper = new ObjectMapper();
		TransactionResult transactionResult = mapper.readValue(jsonStringResponse, TransactionResult.class);
		assertEquals(true, transactionResult.isSuccessful());

		BusinessContactSnapshot created = businessContactService.load(transactionResult.getEntityId());
		assertEquals("john", created.getDetail().getFirstName());

	}
	

	
	@Test
	public void testGet() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		
		ResultActions result = mvc.perform(get("/api/businessContacts"));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		logger.error("Json: " + jsonStringResponse);
		
		ObjectMapper mapper = new ObjectMapper();
		
		BusinessContactSnapshotCollection collection = mapper.readValue(jsonStringResponse, BusinessContactSnapshotCollection.class);
		
		assertEquals(3, collection.getSnapshots().size());
		
		assertEquals(3, collection.getCount());

		assertEquals(3, collection.getTotalItems());
		
	}


	@Test
	public void testGetWithId() throws Exception {

		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();


		ResultActions result = mvc.perform(get("/api/businessContacts/" + myBusinessContact.getId()));

		MvcResult mvcResult = result.andReturn();

		String jsonStringResponse = mvcResult.getResponse().getContentAsString();

		logger.error("Json: " + jsonStringResponse);

		ObjectMapper mapper = new ObjectMapper();

		BusinessContactSnapshot snapshot = mapper.readValue(jsonStringResponse, BusinessContactSnapshot.class);


		assertEquals(myBusinessContact.getDetail().getFirstName(), snapshot.getDetail().getFirstName());

	}


	@Test
	public void testGetWithQuery() throws Exception {
		
		MockMvc mvc = MockMvcBuilders.standaloneSetup(businessContactRestController)
				.build();

		
		String queryText = "WHERE shortName = 'm1'";
		
		ResultActions result = mvc.perform(get("/api/businessContacts?query=WHERE firstName like 'K%'"));
		
		MvcResult mvcResult = result.andReturn();
		
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		
		BusinessContactSnapshotCollection collection = mapper.readValue(jsonStringResponse, BusinessContactSnapshotCollection.class);
		
		assertEquals(1, collection.getSnapshots().size());
		
		assertEquals(1, collection.getCount());

		assertEquals(1, collection.getTotalItems());
		
	}
	
}
