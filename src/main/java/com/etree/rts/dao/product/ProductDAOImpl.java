package com.etree.rts.dao.product;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.products.ArticleCode;
import com.etree.rts.domain.products.ArticleText;
import com.etree.rts.domain.products.Price;
import com.etree.rts.domain.products.Product;
import com.etree.rts.domain.products.SupplierItemPrice;
import com.etree.rts.domain.products.Tag;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class ProductDAOImpl implements ProductDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response addProducts(Product[] products,String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Add supplier data");
		try {
			if (products != null) {
				for (Product product : products) {
					product.setOrganizationId(organizationId);
					if (isProductExist(product.getUuid())) {
						updateProduct(product);
						deleteProductSiblings(product.getUuid());
						addProductSiblings(product);
					} else {
						addProduct(product);
						addProductSiblings(product);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception in addProducts", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	private void addProductSiblings(Product product) throws Exception {
		/**
		 * Save articles
		 */
		if (product.getArticleCodes() != null) {
			for (ArticleCode articleCode : product.getArticleCodes()) {
				articleCode.setOrganizationId(product.getOrganizationId());
				articleCode.setUuid(product.getUuid());
				addArticleCode(articleCode);
			}
		}
		/**
		 * Save ArticleText
		 */

		if (product.getArticleTexts() != null) {
			for (ArticleText articleText : product.getArticleTexts()) {
				articleText.setUuid(product.getUuid());
				addArticleText(articleText);
			}
		}
		/**
		 * Save prices
		 */

		if (product.getPrices() != null) {
			for (Price price : product.getPrices()) {
				price.setUuid(product.getUuid());
				addPrice(price);
			}
		}

		/**
		 * Save supplierItemPrices
		 */

		if (product.getSupplierItemPrices() != null) {
			for (SupplierItemPrice supplierItemPrice : product.getSupplierItemPrices()) {
				supplierItemPrice.setUuid(product.getUuid());
				;
				addSupplierItemPrice(supplierItemPrice);
			}
		}
		/**
		 * Save tag
		 */

		if (product.getTags() != null) {
			for (Tag tag : product.getTags()) {
				tag.setPuid(product.getUuid());
				addTag(tag);
			}
		}
	}

	private void deleteProductSiblings(String uuid) throws Exception {
		/**
		 * Delete articles
		 */
		deleteArticleCode(uuid);
		/**
		 * Delete ArticleText
		 */

		deleteArticleText(uuid);
		/**
		 * Delete prices
		 */

		deletePrice(uuid);

		/**
		 * Delete supplierItemPrices
		 */
		deleteSupplierItemPrice(uuid);
		/**
		 * Delete tag
		 */

		deleteTag(uuid);
	}

	@Override
	public Response addProduct(Product product) throws Exception {
		Response response = CommonUtils.getResponseObject("Add product data");
		try {
			String sql = "INSERT INTO product(organizationId,uuid,sector,recommendedRetailPrice,commodityGroup,requiresSerialNumber ,preparationArticle,"
					+ "itemSequence,activeAssortmentFrom,printTicketsSeparately,costs,alternativeSector,revision,ticketValidityDescription,imageId,"
					+ "activeAssortment,packaging,priceChangeable,name,setType,trackInventory,packagingQuantity,barcode,subArticleSelections,"
					+ "number,infoTexts,deleted,discountable,assortment,producer,basePriceUnit,basePriceMax,basePriceMin,purchasePrice,createdDate,modifiedDate) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { product.getOrganizationId(),product.getUuid(), product.getSector(),
					product.getRecommendedRetailPrice(), product.getCommodityGroup(), product.isRequiresSerialNumber(),
					product.isPreparationArticle(), product.getItemSequence(), product.getActiveAssortmentFrom(),
					product.isPrintTicketsSeparately(), product.getCosts(), product.getAlternativeSector(),
					product.getRevision(), product.getTicketValidityDescription(), product.getImageId(),
					product.isActiveAssortment(), product.isPackaging(), product.isPriceChangeable(), product.getName(),
					product.getSetType(), product.isTrackInventory(), product.getPackagingQuantity(),
					product.getBarcode(), product.getSubArticleSelections(), product.getNumber(),
					product.getInfoTexts(), product.isDeleted(), product.isDiscountable(), product.getAssortment(),
					product.getProducer(), product.getBasePriceUnit(), product.getBasePriceMax(),
					product.getBasePriceMin(), product.getPurchasePrice(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveProduct", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addArticleCode(ArticleCode articleCode) throws Exception {
		Response response = CommonUtils.getResponseObject("Add ArticleCode data");
		try {
			String sql = "INSERT INTO  articleCode(uuid,organizationId,code,quantity) VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { articleCode.getUuid(), articleCode.getOrganizationId(),articleCode.getCode(), articleCode.getQuantity() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addArticleCode", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addArticleText(ArticleText articleText) throws Exception {
		Response response = CommonUtils.getResponseObject("Add ArticleText data");
		try {
			String sql = "INSERT INTO  articleText(uuid,type,text,language) VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { articleText.getUuid(), articleText.getType(),
					articleText.getText(), articleText.getLanguage() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addArticleText", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addPrice(Price price) throws Exception {
		Response response = CommonUtils.getResponseObject("Add price data");
		try {
			String sql = "INSERT INTO  price(uuid,value,pricelist,organizationalUnit,articleCode,validFrom) VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { price.getUuid(), price.getValue(), price.getPricelist(),
					price.getOrganizationalUnit(), price.getArticleCode(), price.getValidFrom() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addPrice", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addSupplierItemPrice(SupplierItemPrice supplierItemPrice) {
		Response response = CommonUtils.getResponseObject("Add supplierItemPrice data");
		try {
			String sql = "INSERT INTO  supplierItemPrice(uuid,id,supplier,orderNumber,price,boxSize,boxDescription) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { supplierItemPrice.getUuid(), supplierItemPrice.getId(),
					supplierItemPrice.getSupplier(), supplierItemPrice.getOrderNumber(), supplierItemPrice.getPrice(),
					supplierItemPrice.getBoxSize(), supplierItemPrice.getBoxDescription() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addSupplierItemPrice", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addTag(Tag tag) throws Exception {
		Response response = CommonUtils.getResponseObject("Add supplier data");
		try {
			String sql = "INSERT INTO  tag(puuid,deleted,revision,uuid,number,name) VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { tag.getPuuid(), tag.isDeleted(), tag.getRevision(),
					tag.getUuid(), tag.getNumber(), tag.getName() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addTag", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public boolean isProductExist(String uuid) {
		try {
			String sql = "SELECT count(uuid) FROM product WHERE uuid=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isProductExist: ", e);
		}
		return false;
	}

	@Override
	public Response updateProduct(Product product) throws Exception {
		Response response = CommonUtils.getResponseObject("Add product data");
		try {
			String sql = "update product set organizationId=?,sector=?,recommendedRetailPrice=?,commodityGroup=?,requiresSerialNumber=? ,preparationArticle=?,"
					+ "itemSequence=?,activeAssortmentFrom=?,printTicketsSeparately=?,costs=?,alternativeSector=?,revision=?,ticketValidityDescription=?,imageId=?,"
					+ "activeAssortment=?,packaging=?,priceChangeable=?,name=?,setType=?,trackInventory=?,packagingQuantity=?,barcode=?,subArticleSelections=?,"
					+ "number=?,infoTexts=?,deleted=?,discountable=?,assortment=?,producer=?,basePriceUnit=?,basePriceMax=?,basePriceMin=?,purchasePrice=?,modifiedDate=? "
					+ " where uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { product.getOrganizationId(),product.getSector(), product.getRecommendedRetailPrice(),
					product.getCommodityGroup(), product.isRequiresSerialNumber(), product.isPreparationArticle(),
					product.getItemSequence(), product.getActiveAssortmentFrom(), product.isPrintTicketsSeparately(),
					product.getCosts(), product.getAlternativeSector(), product.getRevision(),
					product.getTicketValidityDescription(), product.getImageId(), product.isActiveAssortment(),
					product.isPackaging(), product.isPriceChangeable(), product.getName(), product.getSetType(),
					product.isTrackInventory(), product.getPackagingQuantity(), product.getBarcode(),
					product.getSubArticleSelections(), product.getNumber(), product.getInfoTexts(), product.isDeleted(),
					product.isDiscountable(), product.getAssortment(), product.getProducer(),
					product.getBasePriceUnit(), product.getBasePriceMax(), product.getBasePriceMin(),
					product.getPurchasePrice(), new Date(), product.getUuid() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateProduct", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response deleteArticleCode(String uuid) {
		Response response = CommonUtils.getResponseObject("Delete articleCode data");
		try {
			String sql = "DELETE FROM articleCode WHERE uuid=?";
			int rows = jdbcTemplate.update(sql, uuid);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete articleCode data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deleteArticleText(String uuid) {
		Response response = CommonUtils.getResponseObject("Delete ArticleText data");
		try {
			String sql = "DELETE FROM articleText WHERE uuid=?";
			int rows = jdbcTemplate.update(sql, uuid);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete ArticleText data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deletePrice(String uuid) {
		Response response = CommonUtils.getResponseObject("Delete Price data");
		try {
			String sql = "DELETE FROM price WHERE uuid=?";
			int rows = jdbcTemplate.update(sql, uuid);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete price data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deleteSupplierItemPrice(String uuid) {
		Response response = CommonUtils.getResponseObject("Delete SupplierItemPrice data");
		try {
			String sql = "DELETE FROM supplierItemPrice WHERE uuid=?";
			int rows = jdbcTemplate.update(sql, uuid);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete SupplierItemPrice data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deleteTag(String uuid) {
		Response response = CommonUtils.getResponseObject("Delete tag data");
		try {
			String sql = "DELETE FROM tag WHERE puuid=?";
			int rows = jdbcTemplate.update(sql, uuid);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete tag data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public long getRevision(String organizationId) {
		try {
			String sql = "SELECT max(revision) FROM product where organizationId=?";
			Long revision = jdbcTemplate.queryForObject(sql, new Object[] {organizationId}, Long.class);
			return revision;
		} catch (Exception e) {
			logger.error("Exception in isProductExist: ", e);
		}
		return 0;
	}
	
	@Override
	public boolean isArticleCodeExist(String code, String organizationId) {
		try {
			String sql = "SELECT count(code) FROM articleCode WHERE code=? and organizationId=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { code,organizationId }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isArticleCodeExist: ", e);
		}
		return false;
	}
	
	@Override
	public long getProductCount(String organizationId) {
		try {
			String sql = "SELECT count(*) FROM product where organizationId=?";
			Long count = jdbcTemplate.queryForObject(sql, new Object[] {organizationId}, Long.class);
			return count;
		} catch (Exception e) {
			logger.error("Exception in isProductExist: ", e);
		}
		return 0;
	}
}
