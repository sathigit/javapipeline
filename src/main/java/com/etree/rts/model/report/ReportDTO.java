package com.etree.rts.model.report;

import java.io.Serializable;
import java.util.Date;

public class ReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3006138737067954956L;
	private String organizationId;
	private String[] suppliers;
	private Date startDate;
	private Date endDate;
	private String[] commodityGroups;
	private boolean isUAV;
	private boolean isSupplier;
	
	public boolean getIsSupplier() {
		return isSupplier;
	}
	public void setIsSupplier(boolean isSupplier) {
		this.isSupplier = isSupplier;
		
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String[] getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(String[] suppliers) {
		this.suppliers = suppliers;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String[] getCommodityGroups() {
		return commodityGroups;
	}
	public void setCommodityGroups(String[] commodityGroups) {
		this.commodityGroups = commodityGroups;
	}
	public boolean getIsUAV() {
		return isUAV;
	}
	public void setIsUAV(boolean isUAV) {
		this.isUAV = isUAV;
	}
	

}
