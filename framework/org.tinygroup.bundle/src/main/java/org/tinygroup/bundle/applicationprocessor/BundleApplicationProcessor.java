package org.tinygroup.bundle.applicationprocessor;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.xmlparser.node.XmlNode;

public class BundleApplicationProcessor implements ApplicationProcessor{
	private BundleManager bundleManager;
	public BundleManager getBundleManager() {
		return bundleManager;
	}

	public void setBundleManager(BundleManager bundleManager) {
		this.bundleManager = bundleManager;
	}

	public String getApplicationNodePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getComponentConfigPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		// TODO Auto-generated method stub
		
	}

	public XmlNode getComponentConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlNode getApplicationConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void start() {
		bundleManager.setCommonRoot(System.getProperty("user.dir"));
		bundleManager.setBundleRoot(System.getProperty("user.dir"));
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void setApplication(Application application) {
		// TODO Auto-generated method stub
		
	}

}
