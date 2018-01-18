package com.etree.rts.domain.templateconfig;

import java.io.Serializable;
import java.util.List;

public class TemplateFileConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6133808068549435814L;
	private String templateId;
	private String name;
	private String type;
	private String userId;
	private String[] supplierId;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;
	private List<TemplateFileConfigAttribute> templateFileConfigAttributes;

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

	public List<TemplateFileConfigAttribute> getTemplateFileConfigAttributes() {
		return templateFileConfigAttributes;
	}

	public void setTemplateFileConfigAttributes(List<TemplateFileConfigAttribute> templateFileConfigAttributes) {
		this.templateFileConfigAttributes = templateFileConfigAttributes;
	}

}
