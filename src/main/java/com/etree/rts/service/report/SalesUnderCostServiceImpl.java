package com.etree.rts.service.report;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.dao.report.SalesUnderCostDAO;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.report.FullReceipt;
import com.etree.rts.mapper.report.SalesMapper;
import com.etree.rts.model.report.ReportDTO;
import com.etree.rts.model.report.SalesModel;
import com.etree.rts.response.Response;
import com.etree.rts.service.commoditygroup.CommodityGroupService;
import com.etree.rts.service.ksuppliers.KSupplierService;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("salesUnderCostService")
public class SalesUnderCostServiceImpl implements SalesUnderCostService, Constants {

	@Autowired
	SalesUnderCostDAO salesUnderCostDAO;

	@Autowired
	SalesMapper salesMapper;

	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	RTSProperties rtsProperties;

	@Autowired
	KSupplierService kSupplierService;

	@Autowired
	CommodityGroupService commodityGroupService;

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(SalesUnderCostServiceImpl.class);

	public SalesUnderCostServiceImpl() {
		// TODO
	}

	public Response addSalesUnderCosts(String organizationId) {
		Response response = CommonUtils.getResponseObject("Add SalesUnderCosts");
		/**
		 * Sync Korona Suppliers
		 */
		try {
			kSupplierService.syncKSuppliers(organizationId);
		} catch (Exception e) {
			logger.error("Exception while syncKSuppliers: " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
			return response;
		}
		/**
		 * Sync Korona Commodity groups
		 */
		try {
			commodityGroupService.syncCommodityGroups(organizationId);
		} catch (Exception e) {
			logger.error("Exception while syncCommodityGroups: " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
			return response;
		}
		try {
			if (!organizationDAO.isOrganizationExist(organizationId)) {
				return isOrganizationExist(organizationId, response);
			}
			int offset = 0;
			int limit = rtsProperties.getKoronaLimit();
			Date beforeCall = new Date();
			/**
			 * Get Organization Object
			 */
			Organization organization = organizationDAO.getOrg(organizationId);
			while (true) {
				UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaReceiptsUrl())
						.build().expand(organization.getKoronaToken(), salesUnderCostDAO.getRevision(organizationId),
								rtsProperties.getKoronaLimit(), offset);
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				if (object.get("resultList") instanceof JSONArray) {
					JSONArray salesUnderCostsJson = (JSONArray) object.get("resultList");
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					FullReceipt[] salesUnderCosts = mapper.readValue(salesUnderCostsJson.toString(),
							FullReceipt[].class);
					if (salesUnderCosts != null) {
						salesUnderCostDAO.addSalesUnderCosts(salesUnderCosts, organizationId);
						if (salesUnderCosts.length == limit) {
							offset += limit;
						} else {
							response.setStatus(StatusCode.SUCCESS.name());
							break;
						}
					}
				} else {
					response.setStatus(StatusCode.SUCCESS.name());
					break;
				}
			}
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add salesUnderCosts is:" + difference + " seconds");
		} catch (Exception e) {
			logger.error("Exception while fetching salesUnderCosts : " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
		}
		return response;
	}

	private Response isOrganizationExist(String organizationId, Response response) throws Exception {
		response.setMessage("Organization Id[" + organizationId + "] does not exist");
		response.setStatus(StatusCode.ERROR.name());
		return response;
	}

	@Override
	public List<SalesModel> getCommodityGroupByOrganizationId(String organizationId) {
		try {
			return salesUnderCostDAO.getCommodityGroupByOrganizationId(organizationId);
		} catch (Exception ex) {
			logger.info("Exception getCommodityGroups:", ex);
		}
		return null;
	}

	@Override
	public List<SalesModel> getReceiptsByOrganizationId(String organizationId) {
		try {
			return salesUnderCostDAO.getReceiptsByOrganizationId(organizationId);
		} catch (Exception ex) {
			logger.info("Exception getReceipt:", ex);
		}
		return null;
	}

	@Override
	public List<SalesModel> getReceiptsByFilter(ReportDTO reportDTO) {
		try {
			return salesUnderCostDAO.getReceiptsByFilter(reportDTO);
		} catch (Exception ex) {
			logger.info("Exception getReceipt:", ex);
		}
		return null;
	}
}
