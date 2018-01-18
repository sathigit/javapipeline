package com.etree.rts.domain.products;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2599876116241235134L;
	private String organizationId;
	private String uuid;
	private String sector;

	private double recommendedRetailPrice;

	private String commodityGroup;

	private boolean requiresSerialNumber;

	private boolean preparationArticle;

	private long itemSequence;

	private Date activeAssortmentFrom;

	private boolean printTicketsSeparately;

	private double costs;

	private String alternativeSector;

	private long revision;

	private String ticketValidityDescription;

	private String imageId;

	private boolean activeAssortment;

	private boolean packaging;

	private ArticleText[] articleTexts;

	private boolean priceChangeable;

	private String name;

	private Tag[] tags;

	private String setType;

	private boolean trackInventory;

	private int packagingQuantity;

	private String barcode;

	private String subArticleSelections;

	private String number;

	private String infoTexts;

	private boolean deleted;

	private SupplierItemPrice[] supplierItemPrices;

	private String[] subarticleRelations;

	private boolean discountable;

	private ArticleCode[] articleCodes;

	private String assortment;

	private String producer;

	private Price[] prices;

	private String basePriceUnit;

	private double basePriceMax;

	private double basePriceMin;

	private double purchasePrice;
	private String createdDate;
	private String modifiedDate;


	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public double getRecommendedRetailPrice() {
		return recommendedRetailPrice;
	}

	public void setRecommendedRetailPrice(double recommendedRetailPrice) {
		this.recommendedRetailPrice = recommendedRetailPrice;
	}

	public String getCommodityGroup() {
		return commodityGroup;
	}

	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}

	public boolean isRequiresSerialNumber() {
		return requiresSerialNumber;
	}

	public void setRequiresSerialNumber(boolean requiresSerialNumber) {
		this.requiresSerialNumber = requiresSerialNumber;
	}

	public boolean isPreparationArticle() {
		return preparationArticle;
	}

	public void setPreparationArticle(boolean preparationArticle) {
		this.preparationArticle = preparationArticle;
	}

	public long getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(long itemSequence) {
		this.itemSequence = itemSequence;
	}

	public Date getActiveAssortmentFrom() {
		return activeAssortmentFrom;
	}

	public void setActiveAssortmentFrom(Date activeAssortmentFrom) {
		this.activeAssortmentFrom = activeAssortmentFrom;
	}

	public boolean isPrintTicketsSeparately() {
		return printTicketsSeparately;
	}

	public void setPrintTicketsSeparately(boolean printTicketsSeparately) {
		this.printTicketsSeparately = printTicketsSeparately;
	}

	public double getCosts() {
		return costs;
	}

	public void setCosts(double costs) {
		this.costs = costs;
	}

	public String getAlternativeSector() {
		return alternativeSector;
	}

	public void setAlternativeSector(String alternativeSector) {
		this.alternativeSector = alternativeSector;
	}

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public String getTicketValidityDescription() {
		return ticketValidityDescription;
	}

	public void setTicketValidityDescription(String ticketValidityDescription) {
		this.ticketValidityDescription = ticketValidityDescription;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public boolean isActiveAssortment() {
		return activeAssortment;
	}

	public void setActiveAssortment(boolean activeAssortment) {
		this.activeAssortment = activeAssortment;
	}

	public boolean isPackaging() {
		return packaging;
	}

	public void setPackaging(boolean packaging) {
		this.packaging = packaging;
	}

	public ArticleText[] getArticleTexts() {
		return articleTexts;
	}

	public void setArticleTexts(ArticleText[] articleTexts) {
		this.articleTexts = articleTexts;
	}

	public boolean isPriceChangeable() {
		return priceChangeable;
	}

	public void setPriceChangeable(boolean priceChangeable) {
		this.priceChangeable = priceChangeable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public boolean isTrackInventory() {
		return trackInventory;
	}

	public void setTrackInventory(boolean trackInventory) {
		this.trackInventory = trackInventory;
	}

	public int getPackagingQuantity() {
		return packagingQuantity;
	}

	public void setPackagingQuantity(int packagingQuantity) {
		this.packagingQuantity = packagingQuantity;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSubArticleSelections() {
		return subArticleSelections;
	}

	public void setSubArticleSelections(String subArticleSelections) {
		this.subArticleSelections = subArticleSelections;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getInfoTexts() {
		return infoTexts;
	}

	public void setInfoTexts(String infoTexts) {
		this.infoTexts = infoTexts;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SupplierItemPrice[] getSupplierItemPrices() {
		return supplierItemPrices;
	}

	public void setSupplierItemPrices(SupplierItemPrice[] supplierItemPrices) {
		this.supplierItemPrices = supplierItemPrices;
	}

	public String[] getSubarticleRelations() {
		return subarticleRelations;
	}

	public void setSubarticleRelations(String[] subarticleRelations) {
		this.subarticleRelations = subarticleRelations;
	}

	public boolean isDiscountable() {
		return discountable;
	}

	public void setDiscountable(boolean discountable) {
		this.discountable = discountable;
	}

	public ArticleCode[] getArticleCodes() {
		return articleCodes;
	}

	public void setArticleCodes(ArticleCode[] articleCodes) {
		this.articleCodes = articleCodes;
	}

	public String getAssortment() {
		return assortment;
	}

	public void setAssortment(String assortment) {
		this.assortment = assortment;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Price[] getPrices() {
		return prices;
	}

	public void setPrices(Price[] prices) {
		this.prices = prices;
	}

	public String getBasePriceUnit() {
		return basePriceUnit;
	}

	public void setBasePriceUnit(String basePriceUnit) {
		this.basePriceUnit = basePriceUnit;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getBasePriceMax() {
		return basePriceMax;
	}

	public void setBasePriceMax(double basePriceMax) {
		this.basePriceMax = basePriceMax;
	}

	public double getBasePriceMin() {
		return basePriceMin;
	}

	public void setBasePriceMin(double basePriceMin) {
		this.basePriceMin = basePriceMin;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
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
}
