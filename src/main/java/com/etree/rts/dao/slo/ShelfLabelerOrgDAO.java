package com.etree.rts.dao.slo;

import java.util.List;

import com.etree.rts.domain.slo.ShelfLabelerOrg;
import com.etree.rts.response.Response;

public interface ShelfLabelerOrgDAO {

	Response saveShelfLabelerOrg(ShelfLabelerOrg shelfLabelerOrg);
	
	List<ShelfLabelerOrg> getShelfLabelerOrgs() throws Exception;

	Response deleteShelfLabelerOrgs(String name) throws Exception;
	
	String getOrgIdByName(String name) throws Exception;
}
