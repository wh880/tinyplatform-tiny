package org.tinygroup.remoteconfig.config;

import java.io.Serializable;

/**
 * 
 * 客户端配置的配置路径
 * 根据本对象信息可以定位到一组k-v
 * 
 * @author chenjiao
 *
 */
public class ConfigPath implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8247326389867349390L;
	/**
	 * 整个产品的名字,例:tiny
	 */
	private String productName;
	/**
	 * 当前的版本,例:v2.0.33
	 */
	private String versionName;
	/**
	 * 当前的环境
	 */
	private String environmentName;
	/**
	 * 当前模块(moduleId)全路径(不包含产品名),父子模块之间以/分割,例:productModuleId/productManagerModuleId
	 */
	private String modulePath;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
	public String getModulePath() {
		return modulePath;
	}
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	
	
	
	
	
	
}
