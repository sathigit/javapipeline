package com.etree.rts.service.supplier;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.supplier.SupplierDAO;
import com.etree.rts.domain.supplier.Supplier;
import com.etree.rts.mapper.supplier.SupplierMapper;
import com.etree.rts.model.supplier.SupplierModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;


@Service("supplierService")
public class SupplierServiceImpl implements SupplierService, Constants{

	private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Autowired
	SupplierDAO supplierDAO;

	@Autowired
	SupplierMapper supplierMapper;

	public SupplierServiceImpl() {
		// TODO
	}

	@Override
	public Response addSupplier(SupplierModel supplierModel) throws Exception {
		try {
			Supplier supplier = new Supplier();
			BeanUtils.copyProperties(supplierModel, supplier);
			supplier.setSupplierId(CommonUtils.getRandomUUID());
			supplier.setIsActive(true);
			Response response = supplierDAO.addSupplier(supplier);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in addSupplier:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<SupplierModel> getSuppliers()  {
		try {
			List<Supplier> suppliers = supplierDAO.getSuppliers();
			return supplierMapper.entityList(suppliers);
		} catch (Exception ex) {
			logger.info("Exception getSuppliers:", ex);
		}
		return null;
	}
	
	@Override
	public List<SupplierModel> getSuppliersByUser(String userId)  {
		try {
			List<Supplier> suppliers = supplierDAO.getSuppliersByUser(userId);
			return supplierMapper.entityList(suppliers);
		} catch (Exception ex) {
			logger.info("Exception getSuppliers:", ex);
		}
		return null;
	}

	@Override
	public Response updateSupplier(SupplierModel supplierModel) throws Exception {
		try {
			Supplier supplier = new Supplier();
			BeanUtils.copyProperties(supplierModel, supplier);
			return supplierDAO.updateSupplier(supplier);
		} catch (Exception ex) {
			logger.info("Exception in updateSupplier:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public SupplierModel getSupplier(String supplierId) throws Exception {
		try {
			Supplier supplier = supplierDAO.getSupplier(supplierId);
			SupplierModel supplierModel = new SupplierModel();
			if (supplier == null)
				return null;
			BeanUtils.copyProperties(supplier, supplierModel);
			return supplierModel;
		} catch (Exception e) {
			logger.info("Exception in getSupplier:", e);
			return null;
		}
	}

	@Override
	public Response deleteSupplier(String supplierId) {
		try{
			return supplierDAO.deleteSupplier(supplierId);
		} catch (Exception ex) {
			logger.info("Exception in deleteSupplier:" + ex.getMessage());
		}
		return null;

	}

	
	

}
