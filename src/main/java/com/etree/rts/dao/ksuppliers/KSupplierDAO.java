package com.etree.rts.dao.ksuppliers;

import java.util.List;

import com.etree.rts.domain.ksuppliers.KSupplier;
import com.etree.rts.response.Response;

public interface KSupplierDAO {

	Response addKSuppliers(KSupplier[] kSuppliers, String organizationId) throws Exception;

	Response saveKSupplier(KSupplier kSupplier) throws Exception;

	Response updateKSupplier(KSupplier kSupplier) throws Exception;

	long getRevision(String organizationId) throws Exception;

	List<KSupplier> getKSuppliers(String organizationId);

	boolean isKSupplierExist(String uuid);

	long getKSupplierCount(String organizationId);

}
