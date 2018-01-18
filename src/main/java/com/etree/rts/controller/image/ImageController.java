package com.etree.rts.controller.image;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.etree.rts.constant.StatusCode;
import com.etree.rts.model.image.ImageModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.image.ImageService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class ImageController {
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	@Autowired
	ImageService imageService;

	@PostMapping("/image")
	public Response saveImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId,
			@RequestParam("supplierId") String supplierId, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("saveImage: Received request: " + file);
		if (file.isEmpty()) {
			Response res = CommonUtils.getResponseObject("Upload File Error");
			ErrorObject err = CommonUtils.getErrorResponse("File Not Found", "Please select a file to upload");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
			return res;
		}
		ImageModel imageModel = new ImageModel();
		imageModel.setUserId(userId);
		imageModel.setSupplierId(supplierId);
		return imageService.saveImage(imageModel, file);
	}

	@PutMapping("/image")
	public Response updateImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId,
			@RequestParam("imageId") String imageId, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("saveImage: Received request: " + file);
		if (file.isEmpty()) {
			Response res = CommonUtils.getResponseObject("Upload File Error");
			ErrorObject err = CommonUtils.getErrorResponse("File Not Found", "Please select a file to upload");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
			return res;
		}
		ImageModel imageModel = new ImageModel();
		imageModel.setUserId(userId);
		imageModel.setImageId(imageId);
		return imageService.updateImage(imageModel, file);
	}

	@GetMapping("/image/{imageId}")
	@ResponseBody
	public ResponseEntity<Resource> getImage(@PathVariable String imageId) {
		Resource file = null;
		try {
			file = imageService.getImage(imageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@RequestMapping(value = "/imagesByUserAndCategory", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getImagesByUserAndCategory(@RequestBody ImageModel imageModel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ImageModel> imageModels = imageService.getImagesByUserAndCategory(imageModel);
		Response res = CommonUtils.getResponseObject("Image Details");
		if (imageModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Images Not Found", "Images Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(imageModels);
		}
		logger.info("getImagesByUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/images/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getImagesByUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getImagesByUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ImageModel> imageModels = imageService.getImagesByUser(userId);
		Response res = CommonUtils.getResponseObject("Image Detail" + "s");
		if (imageModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Images Not Found", "Images Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(imageModels);
		}
		logger.info("getImagesByUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/image/stock/{imageId}", method = RequestMethod.GET, produces = "text/csv")
	public ResponseEntity<Resource> getImageForStock(@PathVariable String imageId) {
		Resource file = null;
		try {
			file = imageService.getImageForStock(imageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().header("filename", file.getFilename()).body(file);
	}

	@RequestMapping(value = "/image/order/{imageId}", method = RequestMethod.GET, produces = "text/csv")
	public ResponseEntity<Resource> getImageForNewOrder(@PathVariable String imageId) {
		Resource file = null;
		try {
			file = imageService.getImageForNewOrder(imageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().header("filename", file.getFilename()).body(file);
	}

}