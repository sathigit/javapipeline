package com.etree.rts.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.etree.rts.domain.ou.OrganizationUnit;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3114290988319429303L;
	private String userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String mobile;
	private String roleId;
	private String organizationId;
	private String[] organizationUnits;
	private List<OrganizationUnit> organizationUnitList;
	private String orgName;
	private String orgAlias;
	private String packageId;
	private boolean isKoronaUser;
	private boolean isAdmin;
	private boolean isActive;
	private Date createdDate;
	private Date modifiedDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String[] getOrganizationUnits() {
		return organizationUnits;
	}

	public void setOrganizationUnits(String[] organizationUnits) {
		this.organizationUnits = organizationUnits;
	}

	public List<OrganizationUnit> getOrganizationUnitList() {
		return organizationUnitList;
	}

	public void setOrganizationUnitList(List<OrganizationUnit> organizationUnitList) {
		this.organizationUnitList = organizationUnitList;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAlias() {
		return orgAlias;
	}

	public void setOrgAlias(String orgAlias) {
		this.orgAlias = orgAlias;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getIsKoronaUser() {
		return isKoronaUser;
	}

	public void setIsKoronaUser(boolean isKoronaUser) {
		this.isKoronaUser = isKoronaUser;
	}
}
