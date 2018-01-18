package com.etree.rts.dao.shelflabeler;

import java.util.List;

import com.etree.rts.domain.shelfLabeler.ShelfLabeler;
import com.etree.rts.response.Response;

public interface ShelfLabelerDAO {

	Response saveShelfLabel(ShelfLabeler shelfLabeler);

	ShelfLabeler getShelfLabel(String name);

	int getFileMaxVersion(String name);

	List<ShelfLabeler> getShelfLabels(String name) throws Exception;

	List<ShelfLabeler> getShelfLabels() throws Exception;

	List<ShelfLabeler> getShelfLabelsByVersion() throws Exception;

	List<ShelfLabeler> getShelfLabelsByOrg(String organizationId) throws Exception;

	List<ShelfLabeler> getShelfLabelsByLabellerKey(String labellerKey) throws Exception;

	boolean isShelfLabellerNameExist(String name) throws Exception;
}
