package com.onbelay.organizationlib.organization.model;

import java.util.HashMap;
import java.util.Map;

import com.onbelay.core.query.model.BaseColumnDefinitions;
import org.springframework.stereotype.Component;

import com.onbelay.core.query.enums.ColumnDataType;
import com.onbelay.core.query.model.ColumnDefinition;
import com.onbelay.core.query.model.ColumnDefinitions;

@Component(value = "organizationColumnDefinitions")
public class OrganizationColumnDefinitions extends BaseColumnDefinitions implements ColumnDefinitions{

	public static final ColumnDefinition shortName = new ColumnDefinition("shortName", ColumnDataType.STRING, "detail.shortName");
	public static final ColumnDefinition legalName = new ColumnDefinition("legalName", ColumnDataType.STRING, "detail.legalName");
	
	public OrganizationColumnDefinitions() {
		add(shortName);
		add(legalName);
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
