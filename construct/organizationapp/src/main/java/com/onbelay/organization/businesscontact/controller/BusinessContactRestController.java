package com.onbelay.organization.businesscontact.controller;

import com.onbelay.core.controller.BaseRestController;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.core.query.exception.DefinedQueryException;
import com.onbelay.organization.businesscontact.adapter.BusinessContactRestAdapter;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshotCollection;
import com.onbelay.organization.businesscontact.snapshot.BusinessContactSnapshot;
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
@Tag(name="businessContacts", description="BusinessContacts API")
@RequestMapping("/api/businessContacts")
public class BusinessContactRestController extends BaseRestController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private BusinessContactRestAdapter businessContactRestAdapter;
	
	@Operation(summary="Create a business contact")
	@PostMapping(
			produces="application/json",
			consumes="application/json"  )

	public ResponseEntity<TransactionResult> saveBusinessContact(
			@RequestHeader Map<String, String> headers,
			@RequestBody BusinessContactSnapshot snapshot,
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
			result = businessContactRestAdapter.save(snapshot);
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}

	@Operation(summary="Synchronize business contacts")
	@PostMapping(
			value ="/sync",
			produces="application/json")

	public ResponseEntity<TransactionResult> synchronizeBusinessContacts(
			@RequestHeader Map<String, String> headers) {


		TransactionResult result;
		try {
			result = businessContactRestAdapter.synchronizeBusinessContacts();
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}


	@Operation(summary="Create a business contact")
	@PutMapping(
			produces="application/json",
			consumes="application/json"  )

	public ResponseEntity<TransactionResult> saveBusinessContacts(
			@RequestHeader Map<String, String> headers,
			@RequestBody List<BusinessContactSnapshot> snapshots,
			BindingResult bindingResult) {


		if (bindingResult.getErrorCount() > 0) {
			logger.error("Errors on create/update Business Contact PUT");
			for (ObjectError error : bindingResult.getAllErrors()) {
				logger.error(error.toString());
			}

			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}


		TransactionResult result;
		try {
			result = businessContactRestAdapter.save(snapshots);
		} catch (OBRuntimeException p) {
			result = new TransactionResult(p.getErrorCode(), p.getParms());
			result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
		} catch (RuntimeException e) {
			result = new TransactionResult(e.getMessage());
		}
		return processResponse(result);
	}



	@Operation(summary="fetch business contacts")
	@GetMapping(produces = "application/json")
	public ResponseEntity<BusinessContactSnapshotCollection> getBusinessContacts(
			@RequestHeader Map<String, String> headers,
			@RequestParam(value = "query", defaultValue="default") String queryText,
			@RequestParam(value = "start", defaultValue="0")Integer start,
			@RequestParam(value = "limit", defaultValue="100")Integer limit) {
		
		BusinessContactSnapshotCollection collection;
		
		try {
			collection = businessContactRestAdapter.find(
				queryText, 
				start, 
				limit);
		} catch (OBRuntimeException r) {
			collection = new BusinessContactSnapshotCollection(
							errorMessageService.getErrorMessage(r.getErrorCode()));
		} catch (DefinedQueryException r) {
			collection = new BusinessContactSnapshotCollection(r.getMessage());
		} catch (RuntimeException r) {
			collection = new BusinessContactSnapshotCollection(r.getMessage());
		}
		return (ResponseEntity<BusinessContactSnapshotCollection>) processResponse(collection);
	}

	@Operation(summary="get an existing business contact")
	@GetMapping(value="/{id}")
	public ResponseEntity<BusinessContactSnapshot> getBusinessContact(
			@PathVariable Integer id) {
		
		BusinessContactSnapshot snapshot;

		try {
			snapshot = businessContactRestAdapter.load(new EntityId(id));
			if (snapshot == null)
				return new ResponseEntity<BusinessContactSnapshot>(HttpStatus.NOT_FOUND);
		} catch (OBRuntimeException r) {
			snapshot = new BusinessContactSnapshot(r.getErrorCode());
					snapshot.setErrorMessage(errorMessageService.getErrorMessage(r.getErrorCode()));
		} catch (RuntimeException r) {
			snapshot = new BusinessContactSnapshot(r.getMessage());
		}

		return (ResponseEntity<BusinessContactSnapshot>) processResponse(snapshot);
	}

}
