package com.etree.rts.mapper.report;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.report.Sales;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.report.ReportDTO;
@Component
public class SalesMapper extends AbstractModelMapper<ReportDTO, Sales>{

	@Override
	public Class<ReportDTO> entityType() {
		// TODO Auto-generated method stub
		return ReportDTO.class;
	}

	@Override
	public Class<Sales> modelType() {
		// TODO Auto-generated method stub
		return Sales.class;
	}

}
