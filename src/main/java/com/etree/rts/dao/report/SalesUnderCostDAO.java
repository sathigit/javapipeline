package com.etree.rts.dao.report;

import java.util.List;

import com.etree.rts.domain.report.FullReceipt;
import com.etree.rts.domain.report.Receipt;
import com.etree.rts.domain.report.Sales;
import com.etree.rts.model.report.ReportDTO;
import com.etree.rts.model.report.SalesModel;
import com.etree.rts.response.Response;

public interface SalesUnderCostDAO {

	Response addSalesUnderCosts(FullReceipt[] salesUnderCosts, String organizationId) throws Exception;

	Response addReceipt(Receipt receipt) throws Exception;

	Response addSale(Sales sale) throws Exception;

	long getRevision(String organizationId) throws Exception;

	List<SalesModel> getCommodityGroupByOrganizationId(String organizationId);

	List<SalesModel> getReceiptsByOrganizationId(String organizationId);

	List<SalesModel> getReceiptsByFilter(ReportDTO reportDTO);
}

