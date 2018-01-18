package com.etree.rts.model.templateconfig;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class TemplateFileConfigModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -138722920869337285L;
	private String templateId;
	private String name;
	private String type;
	private String userId;
	private String[] supplierId;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;
	private List<TemplateFileConfigAttributeModel> templateFileConfigAttributeModels;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String[] supplierId) {
		this.supplierId = supplierId;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<TemplateFileConfigAttributeModel> getTemplateFileConfigAttributeModels() {
		return templateFileConfigAttributeModels;
	}

	public void setTemplateFileConfigAttributeModels(
			List<TemplateFileConfigAttributeModel> templateFileConfigAttributeModels) {
		this.templateFileConfigAttributeModels = templateFileConfigAttributeModels;
	}

	
}
