package com.etree.rts.dao.image;

import java.util.List;

import com.etree.rts.domain.image.Image;
import com.etree.rts.response.Response;

public interface ImageDAO {

	public List<Image> getImagesByUser(String userId) throws Exception;

	public Response saveImage(Image image) throws Exception;

	public Response updateImage(Image image) throws Exception;

	public Image getImage(String imageId) throws Exception;

	public List<Image> getImagesByUserAndCategory(Image image) throws Exception;
}