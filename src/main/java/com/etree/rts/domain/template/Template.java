package com.etree.rts.domain.template;

import java.io.Serializable;

public class Template implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893893282323406533L;
	private String templateId;
	private String userId;
	private String name;
	private String uniqueIdentifier;
	private String dataIdentifier;
	private String[] supplierId;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getDataIdentifier() {
		return dataIdentifier;
	}

	public void setDataIdentifier(String dataIdentifier) {
		this.dataIdentifier = dataIdentifier;
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

}
