package com.etree.rts.domain.products;

import java.io.Serializable;

public class SupplierItemPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8121775749259322862L;
	private String uuid;
	private long id;
	private double price;
	private String orderNumber;
	private long boxSize;
	private String boxDescription;
	private String supplier;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public long getBoxSize() {
		return boxSize;
	}

	public void setBoxSize(long boxSize) {
		this.boxSize = boxSize;
	}

	public String getBoxDescription() {
		return boxDescription;
	}

	public void setBoxDescription(String boxDescription) {
		this.boxDescription = boxDescription;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}
