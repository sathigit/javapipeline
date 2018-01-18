package com.etree.rts.dao.image;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.image.Image;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class ImageDAOImpl implements ImageDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(ImageDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Image> getImagesByUser(String userId) throws Exception {
		try {
			String sql = "SELECT i.*,s.name as supplierName FROM image i,supplier s where i.userId=? and i.supplierId=s.supplierId order by i.modifiedDate desc";
			return jdbcTemplate.query(sql, new Object[] { userId }, new BeanPropertyRowMapper<Image>(Image.class));
		} catch (Exception e) {
			logger.error("Exception in getImagesByUser", e);
		}
		return null;
	}

	public Response saveImage(Image image) {
		Response response = CommonUtils.getResponseObject("Add image data");
		try {
			String sql = "INSERT INTO image (imageId,supplierId,userId,name,type,size,path,category,isDefault,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { image.getImageId(), image.getSupplierId(), image.getUserId(), image.getName(),
							image.getType(), image.getSize(), image.getPath(), image.getCategory(),
							image.getIsDefault(), image.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(image);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveImage", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	public Response updateImage(Image image) {
		Response response = CommonUtils.getResponseObject("Update image data");
		try {
			String sql = "UPDATE image SET supplierId=?, userId=?,name=?, type=?, size=?, path=?, category=?, isDefault=?,stockOrderPath=?,newProductPath=?,isActive=?,modifiedDate=? WHERE imageId=?";

			int res = jdbcTemplate.update(sql, image.getSupplierId(), image.getUserId(), image.getName(),
					image.getType(), image.getSize(), image.getPath(), image.getCategory(), image.getIsDefault(),
					image.getStockOrderPath(), image.getNewProductPath(), image.getIsActive(), new Date(),
					image.getImageId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update image data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Image getImage(String imageId) {
		try {
			String sql = "SELECT * FROM image where imageId=?";
			return (Image) jdbcTemplate.queryForObject(sql, new Object[] { imageId },
					new BeanPropertyRowMapper(Image.class));
		} catch (Exception e) {
			logger.error("Exception in getImage", e);
			return null;
		}
	}

	public List<Image> getImagesByUserAndCategory(Image image) throws Exception {
		try {
			String sql = "SELECT * FROM image where userId=? and category=? order by modifiedDate";
			return jdbcTemplate.query(sql, new Object[] { image.getUserId(), image.getCategory() },
					new BeanPropertyRowMapper<Image>(Image.class));
		} catch (Exception e) {
			logger.error("Exception in getImagesByUser", e);
		}
		return null;
	}
}