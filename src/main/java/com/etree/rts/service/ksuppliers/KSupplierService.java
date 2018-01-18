package com.etree.rts.service.ksuppliers;

import java.util.List;

import com.etree.rts.domain.ksuppliers.KSupplier;
import com.etree.rts.response.Response;

public interface KSupplierService {

	public Response addKSuppliers(String organizationId) throws Exception;
	
	public Response syncKSuppliers(String organizationId) throws Exception;

	public List<KSupplier> getKSuppliers(String organizationId);

}
