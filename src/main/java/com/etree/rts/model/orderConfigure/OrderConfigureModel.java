package com.etree.rts.model.orderConfigure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class OrderConfigureModel {
	String id;
	String supplierId;
	String templateName;
	String columnIdentifier;
	String dataIdentifier;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getColumnIdentifier() {
		return columnIdentifier;
	}
	public void setColumnIdentifier(String columnIdentifier) {
		this.columnIdentifier = columnIdentifier;
	}
	public String getDataIdentifier() {
		return dataIdentifier;
	}
	public void setDataIdentifier(String dataIdentifier) {
		this.dataIdentifier = dataIdentifier;
	}
	
}
