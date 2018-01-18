package com.etree.rts.model.products;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;



public class ProductModel {
	private String productId;
	private String deleted;
	private int revision;
	private String uuid;
	private String number;
	private String name ;
	private String commodityGroup;
	private String discountable;


	private String sector;
	

	private String assortment;
	private String activeAssortmentFrom;
	private String alternativeSector;

		
	
	private String barcode;	
	private float basePriceMax;
	private String recommendedRetailPrice;
	private float basePriceMin;
	private String basePriceUnit;
	private String itemSequence;
	private String packagingQuantity;
	private String priceChangeable;
	private String infoTexts;
	
	private String activeAssortment;
	private String producer;
	private String ticketValidityDescription;
	private String trackInventory;
	private String requiresSerialNumber;
	private String subArticleSelections;
	

	private String preparationArticle;
	private String packaging;
	private String imageId;
	private float purchasePrice;
	private float costs;
	private String setType;
	private String printTicketsSeparately;
	

	SupplierItemPriceModel[] supplierItemPrices;
	PriceModel[] price;	
	SubarticleRelationModel[] subarticleRelations;	
	ArticleTextModel[] articleTexts;
	ArticleCodeModel[] articleCodes;
	TagModel[] tag;	

	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommodityGroup() {
		return commodityGroup;
	}
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	public String getDiscountable() {
		return discountable;
	}
	public void setDiscountable(String discountable) {
		this.discountable = discountable;
	}
	
	public String getSector() {
		return sector;
	}
    public void setSector(String sector) {
			this.sector = sector;
	}
	public String getAssortment() {
		return assortment;
	}
	public void setAssortment(String assortment) {
		this.assortment = assortment;
	}
	public String getActiveAssortmentFrom() {
		return activeAssortmentFrom;
	}
	public void setActiveAssortmentFrom(String activeAssortmentFrom) {
		this.activeAssortmentFrom = activeAssortmentFrom;
	}
	public String getAlternativeSector() {
		return alternativeSector;
	}
	public void setAlternativeSector(String alternativeSector) {
		this.alternativeSector = alternativeSector;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public float getBasePriceMax() {
		return basePriceMax;
	}
	public void setBasePriceMax(float basePriceMax) {
		this.basePriceMax = basePriceMax;
	}
	public String getRecommendedRetailPrice() {
		return recommendedRetailPrice;
	}
	public void setRecommendedRetailPrice(String recommendedRetailPrice) {
		this.recommendedRetailPrice = recommendedRetailPrice;
	}
	public float getBasePriceMin() {
		return basePriceMin;
	}
	public void setBasePriceMin(float basePriceMin) {
		this.basePriceMin = basePriceMin;
	}
	public String getBasePriceUnit() {
		return basePriceUnit;
	}
	public void setBasePriceUnit(String basePriceUnit) {
		this.basePriceUnit = basePriceUnit;
	}
	public String getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(String itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getPackagingQuantity() {
		return packagingQuantity;
	}
	public void setPackagingQuantity(String packagingQuantity) {
		this.packagingQuantity = packagingQuantity;
	}
	public String getPriceChangeable() {
		return priceChangeable;
	}
	public void setPriceChangeable(String priceChangeable) {
		this.priceChangeable = priceChangeable;
	}
	public String getInfoTexts() {
		return infoTexts;
	}
	public void setInfoTexts(String infoTexts) {
		this.infoTexts = infoTexts;
	}
	
	public String getActiveAssortment() {
		return activeAssortment;
	}
	public void setActiveAssortment(String activeAssortment) {
		this.activeAssortment = activeAssortment;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getTicketValidityDescription() {
		return ticketValidityDescription;
	}
	public void setTicketValidityDescription(String ticketValidityDescription) {
		this.ticketValidityDescription = ticketValidityDescription;
	}
	public String getTrackInventory() {
		return trackInventory;
	}
	public void setTrackInventory(String trackInventory) {
		this.trackInventory = trackInventory;
	}
	public String getRequiresSerialNumber() {
		return requiresSerialNumber;
	}
	public void setRequiresSerialNumber(String requiresSerialNumber) {
		this.requiresSerialNumber = requiresSerialNumber;
	}
	public String getSubArticleSelections() {
		return subArticleSelections;
	}
	public void setSubArticleSelections(String subArticleSelections) {
		this.subArticleSelections = subArticleSelections;
	}
	
	
	public String getPreparationArticle() {
		return preparationArticle;
	}
	public void setPreparationArticle(String preparationArticle) {
		this.preparationArticle = preparationArticle;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public float getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public float getCosts() {
		return costs;
	}
	public void setCosts(float costs) {
		this.costs = costs;
	}
	public String getSetType() {
		return setType;
	}
	public void setSetType(String setType) {
		this.setType = setType;
	}
	public String getPrintTicketsSeparately() {
		return printTicketsSeparately;
	}
	public void setPrintTicketsSeparately(String printTicketsSeparately) {
		this.printTicketsSeparately = printTicketsSeparately;
	}
	public SupplierItemPriceModel[] getSupplierItemPrices() {
		return supplierItemPrices;
	}
	public void setSupplierItemPrices(SupplierItemPriceModel[] supplierItemPrices) {
		this.supplierItemPrices = supplierItemPrices;
	}
	public PriceModel[] getPrice() {
		return price;
	}
	public void setPrice(PriceModel[] price) {
		this.price = price;
	}
	public SubarticleRelationModel[] getSubarticleRelations() {
		return subarticleRelations;
	}
	public void setSubarticleRelations(SubarticleRelationModel[] subarticleRelations) {
		this.subarticleRelations = subarticleRelations;
	}
	public ArticleTextModel[] getArticleTexts() {
		return articleTexts;
	}
	public void setArticleTexts(ArticleTextModel[] articleTexts) {
		this.articleTexts = articleTexts;
	}
	public ArticleCodeModel[] getArticleCodes() {
		return articleCodes;
	}
	public void setArticleCodes(ArticleCodeModel[] articleCodes) {
		this.articleCodes = articleCodes;
	}
	public TagModel[] getTag() {
		return tag;
	}
	public void setTag(TagModel[] tag) {
		this.tag = tag;
	}

	

}
