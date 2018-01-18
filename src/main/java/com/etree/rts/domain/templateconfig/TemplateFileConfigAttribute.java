package com.etree.rts.domain.templateconfig;

public class TemplateFileConfigAttribute {
	private String templateId;
	private String koronaColumn;
	private String supplierColumn;
	private String alias;
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getKoronaColumn() {
		return koronaColumn;
	}
	public void setKoronaColumn(String koronaColumn) {
		this.koronaColumn = koronaColumn;
	}
	public String getSupplierColumn() {
		return supplierColumn;
	}
	public void setSupplierColumn(String supplierColumn) {
		this.supplierColumn = supplierColumn;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
