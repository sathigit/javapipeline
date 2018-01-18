package com.etree.rts.service.product;

import com.etree.rts.response.Response;

public interface ProductService {

	public Response addProducts(String organizationId) throws Exception;

	public Response syncProducts(String organizationId) throws Exception;

	public Response syncProductsIntermediate(String organizationId) throws Exception;
}
