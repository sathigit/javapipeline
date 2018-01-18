package com.etree.rts.domain.ksuppliers;

import java.io.Serializable;

public class KSupplier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3906937527054383142L;
	private String organizationId;
	private String uuid;
	private String name;
	private boolean deleted;
	private int revision;
	private String number;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
