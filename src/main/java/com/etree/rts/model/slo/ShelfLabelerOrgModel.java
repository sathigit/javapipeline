package com.etree.rts.model.slo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ShelfLabelerOrgModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6346677122052865532L;
	private String shelfLabelerId;
	private String userId;
	private String[] organizationId;
	private String name;
	private String fileName;
	private String orgName;
	private String version;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;
	public String getShelfLabelerId() {
		return shelfLabelerId;
	}
	public void setShelfLabelerId(String shelfLabelerId) {
		this.shelfLabelerId = shelfLabelerId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String[] getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String[] organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
