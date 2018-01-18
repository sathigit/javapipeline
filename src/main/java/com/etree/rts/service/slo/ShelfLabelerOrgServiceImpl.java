package com.etree.rts.service.slo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.dao.slo.ShelfLabelerOrgDAO;
import com.etree.rts.domain.slo.ShelfLabelerOrg;
import com.etree.rts.mapper.slo.ShelfLabelerOrgMapper;
import com.etree.rts.model.slo.ShelfLabelerOrgModel;
import com.etree.rts.response.Response;
import com.etree.rts.service.shelflabeler.ShelfLabelerService;

@Service("shelfLabelerOrgService")
public class ShelfLabelerOrgServiceImpl implements ShelfLabelerOrgService {

	private static final Logger logger = LoggerFactory.getLogger(ShelfLabelerService.class);

	@Autowired
	ShelfLabelerOrgDAO shelfLabelerOrgDAO;

	@Autowired
	ShelfLabelerOrgMapper shelfLabelerOrgMapper;

	@Override
	public Response saveShelfLabelerOrg(ShelfLabelerOrgModel shelfLabelerOrgModel) {
		Response response = null;
		try {
			shelfLabelerOrgDAO.deleteShelfLabelerOrgs(shelfLabelerOrgModel.getShelfLabelerId());
			for (String orgId : shelfLabelerOrgModel.getOrganizationId()) {
				ShelfLabelerOrg shelfLabelerOrg = new ShelfLabelerOrg();
				BeanUtils.copyProperties(shelfLabelerOrgModel, shelfLabelerOrg);
				shelfLabelerOrg.setOrganizationId(orgId);
				shelfLabelerOrg.setIsActive(true);
				response = shelfLabelerOrgDAO.saveShelfLabelerOrg(shelfLabelerOrg);
			}
			return response;
		} catch (Exception ex) {
			logger.info("Exception in saveShelfLabelerOrg:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<ShelfLabelerOrgModel> getShelfLabelerOrgs() {
		try {
			List<ShelfLabelerOrg> shelfLabelerOrgs = shelfLabelerOrgDAO.getShelfLabelerOrgs();
			return shelfLabelerOrgMapper.entityList(shelfLabelerOrgs);
		} catch (Exception ex) {
			logger.info("Exception getShelfLabelerOrgs:", ex);
		}
		return null;
	}
}
