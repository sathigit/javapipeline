package com.etree.rts.dao.supplier;

import java.util.List;

import com.etree.rts.domain.supplier.Supplier;
import com.etree.rts.response.Response;

public interface SupplierDAO {

	Response addSupplier(Supplier supplier) throws Exception;

	List<Supplier> getSuppliers() throws Exception;

	List<Supplier> getSuppliersByUser(String userId) throws Exception;

	Response updateSupplier(Supplier supplier) throws Exception;

	Supplier getSupplier(String supplierId) throws Exception;

	Response deleteSupplier(String supplierId);

}
