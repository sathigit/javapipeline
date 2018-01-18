package com.etree.rts.dao.report;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.report.FullReceipt;
import com.etree.rts.domain.report.Receipt;
import com.etree.rts.domain.report.Sales;
import com.etree.rts.model.report.ReportDTO;
import com.etree.rts.model.report.SalesModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.etree.rts.utils.DateUtility;

@Repository
public class SalesUnderCostDAOImpl implements SalesUnderCostDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(SalesUnderCostDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Response addSalesUnderCosts(FullReceipt[] fullReceipts, String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Recipts data");
		try {
			if (fullReceipts != null) {
				for (FullReceipt fullReceipt : fullReceipts) {
					fullReceipt.getReceipt().setOrganizationId(organizationId);
					addReceipt(fullReceipt.getReceipt());
					if (fullReceipt.getSales() != null && fullReceipt.getSales().length > 0) {
						for (Sales sale : fullReceipt.getSales())
							addSale(sale);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception in addSalesUnderCosts", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response addReceipt(Receipt receipt) throws Exception {
		Response response = CommonUtils.getResponseObject("Add salesUnderCost data");
		try {
			String sql = "INSERT INTO receipt(organizationId,uuid,number,pos,posNr,customerGroup ,revision,organizationalUnit,organizationalUnitNr,modifiedTime)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { receipt.getOrganizationId(), receipt.getUuid(), receipt.getNumber(),
							receipt.getPos(), receipt.getPosNr(), receipt.getCustomerGroup(), receipt.getRevision(),
							receipt.getOrganizationalUnit(), receipt.getOrganizationalUnitNr(),
							receipt.getModifiedTime() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveSalesUnderCost", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addSale(Sales sale) throws Exception {
		Response response = CommonUtils.getResponseObject("Add salesUnderCost data");
		try {
			String sql = "INSERT INTO sale(uuid,itemNumber,receiptNumber,cashier,article,articleNr ,articleEAN,description,sector,commodityGroup,receipt,quantity,"
					+ "netItemPrice,baseItemPrice,itemPrice,cost,grossItemPrice,purchasePrice,bookingTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { sale.getUuid(), sale.getItemNumber(), sale.getReceiptNumber(), sale.getCashier(),
							sale.getArticle(), sale.getArticleNr(), sale.getArticleEAN(), sale.getDescription(),
							sale.getSector(), sale.getCommodityGroup(), sale.getReceipt(), sale.getQuantity(),
							sale.getNetItemPrice(), sale.getBaseItemPrice(), sale.getItemPrice(), sale.getCost(),
							sale.getGrossItemPrice(), sale.getPurchasePrice(), sale.getBookingTime() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveSalesUnderCost", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public long getRevision(String organizationId) {
		try {
			String sql = "SELECT max(revision) FROM receipt where organizationId=?";
			Long revision = jdbcTemplate.queryForObject(sql, new Object[] { organizationId }, Long.class);
			return revision;
		} catch (Exception e) {
			logger.error("Exception in getRevision: ", e);
		}
		return 0;
	}

	@Override
	public List<SalesModel> getCommodityGroupByOrganizationId(String organizationId) {
		try {
			String sql = "SELECT distinct(s.commodityGroup) FROM sale s JOIN receipt r ON (s.receipt = r.uuid) where organizationId=?";

			return jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<SalesModel>(SalesModel.class));
		} catch (Exception e) {
			logger.error("Exception in getCommodityGroup", e);

		}
		return null;
	}

	@Override
	public List<SalesModel> getReceiptsByOrganizationId(String organizationId) {
		try {
			String sql = "SELECT s.uuid,r.organizationId,s.itemNumber,s.receiptNumber, r.organizationalUnitNr,s.articleNr,s.description,s.cashier,r.posNr,r.customerGroup,s.sector,s.itemPrice,(s.baseItemPrice-s.netItemPrice) as itemDiscount,s.quantity,s.grossItemPrice,s.netItemPrice,p.purchasePrice, FORMAT((p.purchasePrice-s.netItemPrice),2) as netLoss,s.bookingTime,s.commodityGroup FROM sale s JOIN receipt r ON (s.receipt = r.uuid)  JOIN product p ON (s.articleNr = p.number) where r.organizationId=?";
			return jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<SalesModel>(SalesModel.class));
		} catch (Exception e) {
			logger.error("Exception in getReceiptsByOrganizationId", e);

		}
		return null;
	}

	@Override
	public List<SalesModel> getReceiptsByFilter(ReportDTO reportDTO) {
		try {
		if(reportDTO.getIsSupplier())
		{
			String sql = "SELECT * from sale_receipt_newview  where organizationId='"+reportDTO.getOrganizationId()+"'";
			
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			if (reportDTO.getCommodityGroups() != null && reportDTO.getCommodityGroups().length > 0) {
				sql += " AND commodityGroup in (:commodityGroups)";
				List<String> commodityGroups = Arrays.asList(reportDTO.getCommodityGroups());
				namedParameters.addValue("commodityGroups", commodityGroups);
			}
			if (reportDTO.getSuppliers() != null && reportDTO.getSuppliers().length > 0) {
				sql += " AND supplierId in (:suppliers)";
				List<String> suppliers = Arrays.asList(reportDTO.getSuppliers());
				namedParameters.addValue("suppliers", suppliers);
			}
			if (reportDTO.getStartDate() != null && reportDTO.getEndDate() != null) {
				sql += " and bookingTime between '"
						+ DateUtility.getDateByStringFormat(DateUtility.dateToremoveTime(reportDTO.getStartDate()),
								DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS)
						+ "' and '" + DateUtility.getDateByStringFormat(DateUtility.dateToEOD(reportDTO.getEndDate()),
								DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS)
						+ "'";
			}
			if (reportDTO.getIsUAV()) {
				sql += " AND (cost-netItemPrice) > 0";
			}
			sql += " order by bookingTime desc";
			List<SalesModel> sales = namedParameterJdbcTemplate.query(sql, namedParameters,
			new BeanPropertyRowMapper<SalesModel>(SalesModel.class));

			return sales;
			}
		else {
			String sql = "SELECT * from sale_receipt_view  where organizationId='"+reportDTO.getOrganizationId()+"'";

			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			if (reportDTO.getCommodityGroups() != null && reportDTO.getCommodityGroups().length > 0) {
				sql += " AND commodityGroup in (:commodityGroups)";
				List<String> commodityGroups = Arrays.asList(reportDTO.getCommodityGroups());
				namedParameters.addValue("commodityGroups", commodityGroups);
			}
			if (reportDTO.getSuppliers() != null && reportDTO.getSuppliers().length > 0) {
				sql += " AND supplierId in (:suppliers)";
				List<String> suppliers = Arrays.asList(reportDTO.getSuppliers());
				namedParameters.addValue("suppliers", suppliers);
			}
			if (reportDTO.getStartDate() != null && reportDTO.getEndDate() != null) {
				sql += " and bookingTime between '"
						+ DateUtility.getDateByStringFormat(DateUtility.dateToremoveTime(reportDTO.getStartDate()),
								DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS)
						+ "' and '" + DateUtility.getDateByStringFormat(DateUtility.dateToEOD(reportDTO.getEndDate()),
								DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS)
						+ "'";
			}
			if (reportDTO.getIsUAV()) {
				sql += " AND (cost-netItemPrice) > 0";
			}
			sql += " order by bookingTime desc";
			List<SalesModel> sales = namedParameterJdbcTemplate.query(sql, namedParameters,
					new BeanPropertyRowMapper<SalesModel>(SalesModel.class));

			return sales;
		} 
		}
			catch (Exception e) {
			logger.error("Exception in getReceiptsByOrganizationId", e);

		}
		return null;
	}
}
