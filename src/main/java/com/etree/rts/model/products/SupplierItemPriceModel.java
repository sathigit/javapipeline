package com.etree.rts.model.products;

import org.springframework.stereotype.Repository;


public class SupplierItemPriceModel {
	
	private int productId;
	private int id;
	private String supplier;
	private int orderNumber;
	private float price;
	private int boxSize;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getBoxSize() {
		return boxSize;
	}
	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
	}

}
