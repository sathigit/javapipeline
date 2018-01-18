package com.etree.rts.model.report;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SalesModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1772914591307305926L;
	private String uuid;
	private String organizationId;
	private String itemNumber;
	private String receiptNumber;
	private String organizationalUnitNr;
	private String articleNr;
	private String description;
	private String cashier;
	private String posNr;
	private String customerGroup;
	private String sector;
	private Double itemPrice;
	private Double baseItemPrice;
	private Double itemDiscount;
	private int quantity;
	private Double grossItemPrice;
	private Double netItemPrice;
	private Double purchasePrice;
	private Double netLoss;
	private Date bookingTime;
	private String commodityGroupName;
	private String supplierName;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getOrganizationalUnitNr() {
		return organizationalUnitNr;
	}

	public void setOrganizationalUnitNr(String organizationalUnitNr) {
		this.organizationalUnitNr = organizationalUnitNr;
	}

	public String getArticleNr() {
		return articleNr;
	}

	public void setArticleNr(String articleNr) {
		this.articleNr = articleNr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(Double itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getGrossItemPrice() {
		return grossItemPrice;
	}

	public void setGrossItemPrice(Double grossItemPrice) {
		this.grossItemPrice = grossItemPrice;
	}

	public Double getNetItemPrice() {
		return netItemPrice;
	}

	public void setNetItemPrice(Double netItemPrice) {
		this.netItemPrice = netItemPrice;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getNetLoss() {
		return netLoss;
	}

	public void setNetLoss(Double netLoss) {
		this.netLoss = netLoss;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public Double getBaseItemPrice() {
		return baseItemPrice;
	}

	public void setBaseItemPrice(Double baseItemPrice) {
		this.baseItemPrice = baseItemPrice;
	}

	public String getCommodityGroupName() {
		return commodityGroupName;
	}

	public void setCommodityGroupName(String commodityGroupName) {
		this.commodityGroupName = commodityGroupName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
