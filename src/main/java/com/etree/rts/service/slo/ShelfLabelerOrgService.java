package com.etree.rts.service.slo;

import java.util.List;

import com.etree.rts.model.slo.ShelfLabelerOrgModel;
import com.etree.rts.response.Response;

public interface ShelfLabelerOrgService {

	Response saveShelfLabelerOrg(ShelfLabelerOrgModel shelfLabelerOrgModel);

	List<ShelfLabelerOrgModel> getShelfLabelerOrgs() throws Exception;

}
