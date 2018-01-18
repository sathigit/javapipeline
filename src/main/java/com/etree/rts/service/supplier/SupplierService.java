package com.etree.rts.service.supplier;

import java.util.List;

import com.etree.rts.model.supplier.SupplierModel;
import com.etree.rts.response.Response;

public interface SupplierService {

	Response addSupplier(SupplierModel supplier) throws Exception;

	List<SupplierModel> getSuppliers() throws Exception;

	List<SupplierModel> getSuppliersByUser(String userId) throws Exception;

	Response updateSupplier(SupplierModel supplier) throws Exception;

	SupplierModel getSupplier(String supplierId) throws Exception;

	Response deleteSupplier(String supplierId);

}
