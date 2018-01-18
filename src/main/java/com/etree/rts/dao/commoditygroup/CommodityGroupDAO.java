package com.etree.rts.dao.commoditygroup;

import java.util.List;

import com.etree.rts.domain.commoditygroup.CommodityGroup;
import com.etree.rts.response.Response;

public interface CommodityGroupDAO {

	Response addCommodityGroups(CommodityGroup[] commodityGroups, String organizationId) throws Exception;

	Response saveCommodityGroup(CommodityGroup commodityGroup) throws Exception;

	Response updateCommodityGroup(CommodityGroup commodityGroup) throws Exception;

	long getRevision(String organizationId) throws Exception;

	List<CommodityGroup> getCommodityGroups(String organizationId);

	boolean isCommodityGroupExist(String uuid);

	long getCommodityGroupCount(String organizationId);

}
