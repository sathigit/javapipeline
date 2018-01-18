package com.etree.rts.service.packages;

import java.util.List;

import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.response.Response;

public interface PackageService {

	Response savePackage(PackagesModel packagesModel);

	List<PackagesModel> getPackages();

	PackagesModel getPackage(String packageId, String userId);

	Response updatePackage(PackagesModel packagesModel);

	Response deletePackage(String packageId);

}
