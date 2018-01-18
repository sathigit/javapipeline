package com.etree.rts.domain.image;

import java.io.Serializable;

public class SyncMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1880635775373302642L;
	private String userId;
	private String message;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
