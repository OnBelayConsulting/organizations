package com.onbelay.organizationapp.organization.controller;

import com.onbelay.core.controller.BaseRestController;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.core.query.exception.DefinedQueryException;
import com.onbelay.organizationapp.organization.adapter.OrganizationRestAdapter;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;
import com.onbelay.organizationapp.snapshot.OrganizationSnapshotCollection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name="organizations", description="Organizations API")
@RequestMapping("/api/organizations")
public class OrganizationRestController extends BaseRestController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private OrganizationRestAdapter organizationRestAdapter;
	
	@Operation(summary="Create an organization")
	@PostMapping(
			produces="application/json",
			consumes="application/json"  )

	public ResponseEntity<TransactionResult> saveOrganization(
			@RequestHeader Map<String, String> headers,
			@RequestBody OrganizationSnapshot snapshot,
			BindingResult bindingResult) {


		if (bindingResult.getErrorCount() > 0) {
			logger.error("Errors on create/update FloatIndex PUT");
			for (ObjectError error : bindingResult.getAllErrors()) {
				logger.error(error.toString());
			}

			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}


		TransactionResult result;
		try {
			result = organizationRestAdapter.save(snapshot);
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}

	@Operation(summary="Synchronize organizations")
	@PostMapping(
			value ="/sync",
			produces="application/json")

	public ResponseEntity<TransactionResult> synchronizeOrganizations(
			@RequestHeader Map<String, String> headers) {


		TransactionResult result;
		try {
			result = organizationRestAdapter.synchronizeOrganizations();
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}


	@Operation(summary="Create an organization")
	@PutMapping(
			produces="application/json",
			consumes="application/json"  )

	public ResponseEntity<TransactionResult> saveOrganizations(
			@RequestHeader Map<String, String> headers,
			@RequestBody List<OrganizationSnapshot> snapshots,
			BindingResult bindingResult) {


		if (bindingResult.getErrorCount() > 0) {
			logger.error("Errors on create/update FloatIndex PUT");
			for (ObjectError error : bindingResult.getAllErrors()) {
				logger.error(error.toString());
			}

			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}


		TransactionResult result;
		try {
			result = organizationRestAdapter.save(snapshots);
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}



	@Operation(summary="fetch organizations")
	@GetMapping(produces = "application/json")
	public ResponseEntity<OrganizationSnapshotCollection> getOrganizations(
			@RequestHeader Map<String, String> headers,
			@RequestParam(value = "query", defaultValue="default") String queryText,
			@RequestParam(value = "start", defaultValue="0")Integer start,
			@RequestParam(value = "limit", defaultValue="100")Integer limit) {
		
		OrganizationSnapshotCollection collection;
		
		try {
			collection = organizationRestAdapter.find(
				queryText, 
				start, 
				limit);
		} catch (OBRuntimeException r) {
			collection = new OrganizationSnapshotCollection(
							errorMessageService.getErrorMessage(r.getErrorCode()));
		} catch (DefinedQueryException r) {
			collection = new OrganizationSnapshotCollection(r.getMessage());
		} catch (RuntimeException r) {
			collection = new OrganizationSnapshotCollection(r.getMessage());
		}
		return (ResponseEntity<OrganizationSnapshotCollection>) processResponse(collection);
	}

	@Operation(summary="get an existing organization")
	@GetMapping(value="/{id}")
	public ResponseEntity<OrganizationSnapshot> getOrganization(
			@PathVariable Integer id) {
		
		OrganizationSnapshot snapshot;

		try {
			snapshot = organizationRestAdapter.load(new EntityId(id));
			if (snapshot == null)
				return new ResponseEntity<OrganizationSnapshot>(HttpStatus.NOT_FOUND);
		} catch (OBRuntimeException r) {
			snapshot = new OrganizationSnapshot(r.getErrorCode());
					snapshot.setErrorMessage(errorMessageService.getErrorMessage(r.getErrorCode()));
		} catch (RuntimeException r) {
			snapshot = new OrganizationSnapshot(r.getMessage());
		}

		return (ResponseEntity<OrganizationSnapshot>) processResponse(snapshot);
	}

}
