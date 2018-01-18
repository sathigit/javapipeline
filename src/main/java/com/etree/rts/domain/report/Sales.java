package com.etree.rts.domain.report;

import java.io.Serializable;
import java.util.Date;

public class Sales implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3726766008629509145L;

	private String uuid;
	private String itemNumber;
	private String receiptNumber;
	private String cashier;
	private String article;
	private String articleNr;
	private String articleEAN;
	private String description;
	private String sector;
	private String commodityGroup;
	private String receipt;
	private int quantity;
	private Double netItemPrice;
	private Double baseItemPrice;
	private Double itemPrice;
	private Double cost;
	private Double grossItemPrice;
	private Double purchasePrice;
	private Date bookingTime;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getArticleNr() {
		return articleNr;
	}
	public void setArticleNr(String articleNr) {
		this.articleNr = articleNr;
	}
	public String getArticleEAN() {
		return articleEAN;
	}
	public void setArticleEAN(String articleEAN) {
		this.articleEAN = articleEAN;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getCommodityGroup() {
		return commodityGroup;
	}
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getNetItemPrice() {
		return netItemPrice;
	}
	public void setNetItemPrice(Double netItemPrice) {
		this.netItemPrice = netItemPrice;
	}
	public Double getBaseItemPrice() {
		return baseItemPrice;
	}
	public void setBaseItemPrice(Double baseItemPrice) {
		this.baseItemPrice = baseItemPrice;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getGrossItemPrice() {
		return grossItemPrice;
	}
	public void setGrossItemPrice(Double grossItemPrice) {
		this.grossItemPrice = grossItemPrice;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Date getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	
}
