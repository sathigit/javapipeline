package com.etree.rts.mapper.orderConfigure;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.orderConfigure.OrderConfigure;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.orderConfigure.OrderConfigureModel;



@Component
public class OrderConfigureMapper extends AbstractModelMapper<OrderConfigureModel, OrderConfigure> {

	@Override
	public Class<OrderConfigureModel> entityType() {
		
		return OrderConfigureModel.class;
	}

	@Override
	public Class<OrderConfigure> modelType() {
		
		return OrderConfigure.class;
	}

}
