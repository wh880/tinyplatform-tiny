package org.tinygroup.bundle.service;

import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;

public class BundleService {
	private BundleManager bundleManager;

	public BundleManager getBundleManager() {
		return bundleManager;
	}

	public void setBundleManager(BundleManager bundleManager) {
		this.bundleManager = bundleManager;
	}

	public void addBundle(BundleDefine bundleDefine) {
		bundleManager.addBundleDefine(bundleDefine);
	}

	public void startBundle(BundleDefine bundleDefine) {
		bundleManager.start(bundleDefine);
	}

	public void stopBundle(BundleDefine bundleDefine) {
		bundleManager.stop(bundleDefine.getName());
	}

	public void removeBundle(BundleDefine bundleDefine) throws BundleException {
		bundleManager.removeBundle(bundleDefine);

	}
}
