package com.onbelay.organization.organization.model;

import com.onbelay.core.query.model.BaseColumnDefinitions;
import org.springframework.stereotype.Component;

import com.onbelay.core.query.enums.ColumnDataType;
import com.onbelay.core.query.model.ColumnDefinition;
import com.onbelay.core.query.model.ColumnDefinitions;

@Component(value = "organizationColumnDefinitions")
public class OrganizationColumnDefinitions extends BaseColumnDefinitions implements ColumnDefinitions{

	public static final ColumnDefinition organizationId = new ColumnDefinition("organizationId", ColumnDataType.INTEGER, "id");
	public static final ColumnDefinition shortName = new ColumnDefinition("shortName", ColumnDataType.STRING, "detail.shortName");
	public static final ColumnDefinition legalName = new ColumnDefinition("legalName", ColumnDataType.STRING, "detail.legalName");
	public static final ColumnDefinition creditStatus = new ColumnDefinition("creditStatus", ColumnDataType.STRING, "detail.creditStatusCodeValue");
	public static final ColumnDefinition industryType = new ColumnDefinition("industryType", ColumnDataType.STRING, "detail.industryTypeCodeValue");

	public OrganizationColumnDefinitions() {
		add(organizationId);
		add(shortName);
		add(legalName);
		add(creditStatus);
		add(industryType);
	}

	@Override
	public String getCodeName() {
		return shortName.getPath();
	}

	@Override
	public String getDescriptionName() {
		return shortName.getPath();
	}
}
