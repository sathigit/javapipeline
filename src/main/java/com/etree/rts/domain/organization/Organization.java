package com.etree.rts.domain.organization;

import java.io.Serializable;
import java.sql.Date;

public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2675612307526271415L;
	private String organizationId;
	private String name;
	private String alias;
	private String labellerKey;
	private String koronaApiId;
	private String koronaSecret;
	private String koronaToken;
	private String lcbApiKey;
	private String lcbMmeCode;
	private String packageId;
	private String userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private boolean isActive;
	private Date createdDate;
	private Date modifiedDate;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLabellerKey() {
		return labellerKey;
	}

	public void setLabellerKey(String labellerKey) {
		this.labellerKey = labellerKey;
	}

	public String getKoronaApiId() {
		return koronaApiId;
	}

	public void setKoronaApiId(String koronaApiId) {
		this.koronaApiId = koronaApiId;
	}

	public String getKoronaSecret() {
		return koronaSecret;
	}

	public void setKoronaSecret(String koronaSecret) {
		this.koronaSecret = koronaSecret;
	}

	public String getKoronaToken() {
		return koronaToken;
	}

	public void setKoronaToken(String koronaToken) {
		this.koronaToken = koronaToken;
	}

	public String getLcbApiKey() {
		return lcbApiKey;
	}

	public void setLcbApiKey(String lcbApiKey) {
		this.lcbApiKey = lcbApiKey;
	}

	public String getLcbMmeCode() {
		return lcbMmeCode;
	}

	public void setLcbMmeCode(String lcbMmeCode) {
		this.lcbMmeCode = lcbMmeCode;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
