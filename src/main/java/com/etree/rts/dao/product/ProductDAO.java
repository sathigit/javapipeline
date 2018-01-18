package com.etree.rts.dao.product;

import com.etree.rts.domain.products.ArticleCode;
import com.etree.rts.domain.products.ArticleText;
import com.etree.rts.domain.products.Price;
import com.etree.rts.domain.products.Product;
import com.etree.rts.domain.products.SupplierItemPrice;
import com.etree.rts.domain.products.Tag;
import com.etree.rts.response.Response;

public interface ProductDAO {

	Response addProducts(Product[] products, String organizationId) throws Exception;

	Response addProduct(Product product) throws Exception;

	Response updateProduct(Product product) throws Exception;

	Response addArticleCode(ArticleCode articleCode) throws Exception;

	Response addArticleText(ArticleText articleText) throws Exception;

	Response addPrice(Price price) throws Exception;

	Response addSupplierItemPrice(SupplierItemPrice supplierItemPrice) throws Exception;

	Response addTag(Tag tag) throws Exception;

	boolean isProductExist(String uuid) throws Exception;

	Response deleteArticleCode(String uuid) throws Exception;

	Response deleteArticleText(String uuid) throws Exception;

	Response deletePrice(String uuid) throws Exception;

	Response deleteSupplierItemPrice(String uuid) throws Exception;

	Response deleteTag(String uuid) throws Exception;

	long getRevision(String organizationId) throws Exception;

	boolean isArticleCodeExist(String code, String organizationId) throws Exception;

	long getProductCount(String organizationId) throws Exception;
}
