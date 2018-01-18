package com.etree.rts.service.commoditygroup;

import java.util.List;

import com.etree.rts.domain.commoditygroup.CommodityGroup;
import com.etree.rts.response.Response;

public interface CommodityGroupService {

	public Response addCommodityGroups(String organizationId) throws Exception;
	
	public Response syncCommodityGroups(String organizationId) throws Exception;

	public List<CommodityGroup> getCommodityGroups(String organizationId);

}
