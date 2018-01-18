package com.etree.rts.domain.report;

import java.io.Serializable;
import java.util.Date;

public class Receipt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2322293174361811882L;

	private String organizationId;
	private String uuid;
	private String number;
	private String pos;
	private String posNr;
	private String customerGroup;
	private long revision;
	private String organizationalUnit;
	private String organizationalUnitNr;
	private Date modifiedTime;
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getPosNr() {
		return posNr;
	}
	public void setPosNr(String posNr) {
		this.posNr = posNr;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public long getRevision() {
		return revision;
	}
	public void setRevision(long revision) {
		this.revision = revision;
	}
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}
	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}
	public String getOrganizationalUnitNr() {
		return organizationalUnitNr;
	}
	public void setOrganizationalUnitNr(String organizationalUnitNr) {
		this.organizationalUnitNr = organizationalUnitNr;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


}
