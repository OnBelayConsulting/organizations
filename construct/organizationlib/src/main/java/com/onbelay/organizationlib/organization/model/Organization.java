/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
*/
package com.onbelay.organizationlib.organization.model;

import com.onbelay.core.entity.model.AuditAbstractEntity;
import com.onbelay.core.entity.model.TemporalAbstractEntity;
import com.onbelay.core.exception.OBValidationException;
import com.onbelay.organizationlib.organization.shared.OrganizationDetail;
import com.onbelay.organizationlib.organization.snapshot.OrganizationSnapshot;

import jakarta.persistence.*;

@Entity
@Table(name = "OB_ORGANIZATION")
@NamedQueries({
    @NamedQuery(
       name = OrganizationRepositoryBean.FETCH_ALL_ORGANIZATIONS,
       query = "SELECT organization "
       		+ "   FROM Organization organization " +
       	     "ORDER BY organization.detail.shortName DESC"),
	@NamedQuery(
		name = OrganizationRepositoryBean.FIND_BY_SHORT_NAME,
		query = "SELECT organization "
			+ "    FROM Organization organization " +
				"WHERE organization.detail.shortName = :shortName")
})
public class Organization extends TemporalAbstractEntity {
	
	private Integer id;
	private OrganizationDetail detail = new OrganizationDetail();
	
	protected Organization() {
		
	}
	
	@Override
	@Transient
	public String getEntityName() {
		return "Organization";
	}

	
	@Id
    @Column(name="ENTITY_ID", insertable = false, updatable = false)
    @SequenceGenerator(name="OrganizationGen", initialValue=1, sequenceName="ORGANIZATION_SEQ", allocationSize = 1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "OrganizationGen")
    
	public Integer getId() {
		return id;
	}
	
	private void setId(Integer organizationId) {
		this.id = organizationId;
	}

	public static Organization create(OrganizationSnapshot snapshot) {
		Organization organization = new Organization();
		organization.createWith(snapshot);
		return organization;
	}

	@Override
	protected void validate() throws OBValidationException {
		super.validate();
		detail.validate();
	}

	protected void createWith(OrganizationSnapshot snapshot) {
		super.createWith(snapshot);
		detail.copyFrom(snapshot.getDetail());
		save();
	}
	
	public void updateWith(OrganizationSnapshot snapshot) {
		super.updateWith(snapshot);
		detail.copyFrom(snapshot.getDetail());
		update();
	}
	
	@Embedded
	public OrganizationDetail getDetail() {
		return detail;
	}
	
	protected void setDetail(OrganizationDetail detail) {
		this.detail = detail;
	}

	@Override
	protected AuditAbstractEntity createHistory() {
		return OrganizationAudit.create(this);
	}

	@Override
	public AuditAbstractEntity fetchRecentHistory() {
		return OrganizationAudit.findRecentHistory(this);
	}


}
