package com.etree.rts.model.templateconfig;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TemplateFileConfigAttributeModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8441097753795847569L;
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
