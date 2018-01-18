package com.etree.rts.utils;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.dao.product.ProductDAO;
import com.etree.rts.service.orderConfigure.OrderConfigureServiceImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJSON {

	@Autowired
	RTSProperties rtsProperties;
	private static final Logger logger = LoggerFactory.getLogger(OrderConfigureServiceImpl.class);

	@Autowired
	ProductDAO productDAO;

	public void getJsonObject() throws ParseException {

		try {
			int offset = 0;
			int limit = rtsProperties.getKoronaLimit();
			while (true) {
				UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaProductsUrl()).build()
						.expand("", rtsProperties.getKoronaLimit(), offset);
				RestTemplate restTemplate = new RestTemplate();
				ObjectMapper mapper = new ObjectMapper();
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				String productsJson = object.getJSONObject("resultList").toString();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//				List<Product> products = (List<Product>) mapper.readValue(productsJson, Product.class);
//				if (products != null && !products.isEmpty()) {
//					productDAO.addProducts(products);
//					if (products.size() == limit) {
//						offset += limit;
//					} else {
//						break;
//					}
//				}
			}
		} catch (Exception e) {
			logger.error("Exception while fetching products : " + " ", e);
		}
	}
}