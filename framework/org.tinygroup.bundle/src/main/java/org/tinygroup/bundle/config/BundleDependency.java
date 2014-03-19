package org.tinygroup.bundle.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("bundle-dependency")
public class BundleDependency {
	@XStreamAlias("bundle-id")
	@XStreamAsAttribute
	private String bundleId;
	@XStreamAlias("service-id")
	@XStreamAsAttribute
	private String serviceId;
	@XStreamAlias("service-type")
	@XStreamAsAttribute
	private String serviceType;
	@XStreamAlias("bundle-version")
	@XStreamAsAttribute
	private String bundleVersion;
	@XStreamAlias("service-version")
	@XStreamAsAttribute
	private String serviceVersion;

	public String getBundleVersion() {
		return bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
