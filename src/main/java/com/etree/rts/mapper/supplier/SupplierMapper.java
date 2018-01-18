package com.etree.rts.mapper.supplier;

import java.util.List;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.orderConfigure.OrderConfigure;
import com.etree.rts.domain.supplier.Supplier;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.orderConfigure.OrderConfigureModel;
import com.etree.rts.model.supplier.SupplierModel;



@Component
public class SupplierMapper extends AbstractModelMapper<SupplierModel, Supplier> {


	@Override
	public Class<SupplierModel> entityType() {
		
		return SupplierModel.class;
	}

	@Override
	public Class<Supplier> modelType() {
		
		return Supplier.class;
	}

}

