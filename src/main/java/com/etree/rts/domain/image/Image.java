package com.etree.rts.domain.image;

import java.io.Serializable;
import java.util.Date;

public class Image implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8635879683995284962L;
	private String imageId;
	private String supplierId;
	private String userId;
	private String name;
	private String type;
	private long size;
	private String path;
	private String category;
	private boolean isDefault;
	private String stockOrderPath;
	private String newProductPath;
	private boolean isActive;
	private Date modifiedDate;
	private Date createdDate;
	private String supplierName;
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getStockOrderPath() {
		return stockOrderPath;
	}
	public void setStockOrderPath(String stockOrderPath) {
		this.stockOrderPath = stockOrderPath;
	}
	public String getNewProductPath() {
		return newProductPath;
	}
	public void setNewProductPath(String newProductPath) {
		this.newProductPath = newProductPath;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	

}
