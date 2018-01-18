package com.etree.rts.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("RTS")
public class RTSProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1104118810026296091L;
	private String headerApiKey;
	private String imagePath;
	private String koronaProductsUrl;
	private int koronaLimit;
	private String koronaProductsUpdateUrl;
	private int syncLimit;
	private String koronaUsersUrl;
	private String koronaOrganizationalUnitsUrl;
	private String coreMenuPath;
	private String koronaReceiptsUrl;
	private String koronaSuppliersUrl;
	private String koronaSuppliersUpdateUrl;
	private String koronaCommodityGroupUrl;
	private String koronaCommodityGroupUpdateUrl;
	public String getHeaderApiKey() {
		return headerApiKey;
	}

	public void setHeaderApiKey(String headerApiKey) {
		this.headerApiKey = headerApiKey;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getKoronaProductsUrl() {
		return koronaProductsUrl;
	}

	public void setKoronaProductsUrl(String koronaProductsUrl) {
		this.koronaProductsUrl = koronaProductsUrl;
	}

	public int getKoronaLimit() {
		return koronaLimit;
	}

	public void setKoronaLimit(int koronaLimit) {
		this.koronaLimit = koronaLimit;
	}

	public String getKoronaProductsUpdateUrl() {
		return koronaProductsUpdateUrl;
	}

	public void setKoronaProductsUpdateUrl(String koronaProductsUpdateUrl) {
		this.koronaProductsUpdateUrl = koronaProductsUpdateUrl;
	}

	public int getSyncLimit() {
		return syncLimit;
	}

	public void setSyncLimit(int syncLimit) {
		this.syncLimit = syncLimit;
	}

	public String getKoronaUsersUrl() {
		return koronaUsersUrl;
	}

	public void setKoronaUsersUrl(String koronaUsersUrl) {
		this.koronaUsersUrl = koronaUsersUrl;
	}

	public String getKoronaOrganizationalUnitsUrl() {
		return koronaOrganizationalUnitsUrl;
	}

	public void setKoronaOrganizationalUnitsUrl(String koronaOrganizationalUnitsUrl) {
		this.koronaOrganizationalUnitsUrl = koronaOrganizationalUnitsUrl;
	}

	public String getCoreMenuPath() {
		return coreMenuPath;
	}

	public void setCoreMenuPath(String coreMenuPath) {
		this.coreMenuPath = coreMenuPath;
	}

	public String getKoronaReceiptsUrl() {
		return koronaReceiptsUrl;
	}

	public void setKoronaReceiptsUrl(String koronaReceiptsUrl) {
		this.koronaReceiptsUrl = koronaReceiptsUrl;
	}

	public String getKoronaSuppliersUrl() {
		return koronaSuppliersUrl;
	}

	public void setKoronaSuppliersUrl(String koronaSuppliersUrl) {
		this.koronaSuppliersUrl = koronaSuppliersUrl;
	}

	public String getKoronaSuppliersUpdateUrl() {
		return koronaSuppliersUpdateUrl;
	}

	public void setKoronaSuppliersUpdateUrl(String koronaSuppliersUpdateUrl) {
		this.koronaSuppliersUpdateUrl = koronaSuppliersUpdateUrl;
	}

	public String getKoronaCommodityGroupUrl() {
		return koronaCommodityGroupUrl;
	}

	public void setKoronaCommodityGroupUrl(String koronaCommodityGroupUrl) {
		this.koronaCommodityGroupUrl = koronaCommodityGroupUrl;
	}

	public String getKoronaCommodityGroupUpdateUrl() {
		return koronaCommodityGroupUpdateUrl;
	}

	public void setKoronaCommodityGroupUpdateUrl(String koronaCommodityGroupUpdateUrl) {
		this.koronaCommodityGroupUpdateUrl = koronaCommodityGroupUpdateUrl;
	}

}
