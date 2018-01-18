package com.etree.rts.dao.packages;

import java.util.List;

import com.etree.rts.domain.packages.Packages;
import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.response.Response;

public interface PackageDAO {

	Response savePackage(PackagesModel packagesModel) throws Exception;

	List<Packages> getPackages() throws Exception;

	Packages getPackage(String packageId) throws Exception;

	Response deletePackage(String packageId);

	Response updatePackage(PackagesModel packagesModel) throws Exception;
}
