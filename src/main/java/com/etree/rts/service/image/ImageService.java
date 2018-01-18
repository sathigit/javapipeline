package com.etree.rts.service.image;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.etree.rts.model.image.ImageModel;
import com.etree.rts.response.Response;

public interface ImageService {

	public List<ImageModel> getImagesByUser(String userId) throws Exception;

	public Response saveImage(ImageModel imageModel, MultipartFile file) throws Exception;

	public Response updateImage(ImageModel imageModel, MultipartFile file) throws Exception;

	public Resource getImage(String ImageId) throws Exception;

	public List<ImageModel> getImagesByUserAndCategory(ImageModel imageModel) throws Exception;
	
	public Resource getImageForStock(String ImageId) throws Exception;

	public Resource getImageForNewOrder(String ImageId) throws Exception;
}
