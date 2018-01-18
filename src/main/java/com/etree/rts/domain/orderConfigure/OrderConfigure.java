package com.etree.rts.domain.orderConfigure;

public class OrderConfigure {
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
