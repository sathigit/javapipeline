package com.etree.rts.service.report;

import java.util.List;

import com.etree.rts.model.report.ReportDTO;
import com.etree.rts.model.report.SalesModel;
import com.etree.rts.response.Response;

public interface SalesUnderCostService {

	public Response addSalesUnderCosts(String organizationId) throws Exception;

	public List<SalesModel> getCommodityGroupByOrganizationId(String organizationId);

	public List<SalesModel> getReceiptsByOrganizationId(String organizationId);

	public List<SalesModel> getReceiptsByFilter(ReportDTO reportDTO);
}
