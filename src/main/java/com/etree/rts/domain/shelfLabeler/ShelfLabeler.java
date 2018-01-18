package com.etree.rts.domain.shelfLabeler;

import java.io.Serializable;

public class ShelfLabeler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String shelfLabelerId;
	private String name;
	private String userId;
	private String fileName;
	private byte[] content;
	private int version;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;
	
	
	public String getShelfLabelerId() {
		return shelfLabelerId;
	}
	public void setShelfLabelerId(String shelfLabelerId) {
		this.shelfLabelerId = shelfLabelerId;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
