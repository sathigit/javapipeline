package com.etree.rts.service.shelflabeler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etree.rts.model.shelfLabeler.ShelfLabelerModel;
import com.etree.rts.response.Response;

public interface ShelfLabelerService {

	Response saveShelfLabel(ShelfLabelerModel shelfLabelerModel, MultipartFile file);

	ShelfLabelerModel getShelfLabel(String name);

	List<ShelfLabelerModel> getShelfLabels() throws Exception;

	List<ShelfLabelerModel> getShelfLabels(String name) throws Exception;

	List<ShelfLabelerModel> getShelfLabelsByVersion() throws Exception;

	List<ShelfLabelerModel> getShelfLabelsByOrg(String organizationId) throws Exception;

	List<ShelfLabelerModel> getShelfLabelsByLabellerKey(String labellerKey) throws Exception;

	boolean isShelfLabellerNameExist(String name) throws Exception;
}
