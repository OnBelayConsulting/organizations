/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organizationlib.organization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.onbelay.core.entity.component.ApplicationContextFactory;
import com.onbelay.core.entity.model.AuditAbstractEntity;
import com.onbelay.core.entity.model.TemporalAbstractEntity;
import com.onbelay.core.entity.repository.BaseRepository;
import com.onbelay.core.utils.DateUtils;
import com.onbelay.organizationlib.organization.shared.OrganizationDetail;

@Entity
@Table(name = "ORGANIZATION_AUDIT")
@NamedQueries({
    @NamedQuery(
       name = OrganizationAudit.FIND_AUDIT_BY_TO_DATE,
       query = "SELECT organizationAudit " +
			   "  FROM OrganizationAudit organizationAudit " +
       		    "WHERE organizationAudit.historyDateTimeStamp.validToDateTime = :date " +
       		      "AND organizationAudit.organization = :organization")
})

public class OrganizationAudit extends AuditAbstractEntity {
	public static final String FIND_AUDIT_BY_TO_DATE = "OrganizationAudit.FIND_AUDIT_BY_TO_DATE";
	private Integer id;
	
	private Organization organization;
	
	private OrganizationDetail detail = new OrganizationDetail();
	
	
	protected OrganizationAudit() {
		
	}
	
    protected static OrganizationAudit create(Organization organization) {
    	OrganizationAudit audit = new OrganizationAudit();
    	audit.organization = organization;
    	audit.copyFrom(organization);
    	return audit;
	}

	@Id
	@Column(name="AUDIT_ID", updatable = false, insertable = false)
    @SequenceGenerator(name="OrganizationAuditGen", sequenceName="ORGANIZATION_AUDIT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "OrganizationAuditGen")
    
	public Integer getId() {
		return id;
	}
	
	private void setId(Integer organizationId) {
		this.id = organizationId;
	}
	
	@ManyToOne
	@JoinColumn(name ="ENTITY_ID")
	public Organization getOrganization() {
		return organization;
	}

	private void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Embedded
	public OrganizationDetail getDetail() {
		return detail;
	}
	
	protected void setDetail(OrganizationDetail detail) {
		this.detail = detail;
	}


	public static OrganizationAudit findRecentHistory(Organization organization) {
		String[] parmNames = {"organization", "date" };
		Object[] parms =     {organization,   DateUtils.getValidToDateTime()};

		return (OrganizationAudit) getAuditEntityRepository().executeSingleResultQuery(
				FIND_AUDIT_BY_TO_DATE,
				parmNames,
				parms);

	}

	@Override
	@Transient
	public TemporalAbstractEntity getParent() {
		return organization;
	}

	@Override
	public void copyFrom(TemporalAbstractEntity entity) {
		Organization organization = (Organization) entity;
		this.detail.copyFrom(organization.getDetail());
		
	}
	
	
	

}
