/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.manager.ProductManager;
import org.tinygroup.remoteconfig.manager.VersionManager;
import org.tinygroup.remoteconfig.zk.client.ZKProductManager;

/**
 * @author yanwj06282
 *
 */
public class ProductManagerImpl implements ProductManager {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductManagerImpl.class);
	
	VersionManager versionManager;
	
	public void setVersionManager(VersionManager versionManager) {
		this.versionManager = versionManager;
	}
	
	public Product add(Product product) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，增加项目[%s]" ,product.getName());
		try {
			ZKProductManager.set(product.getName(), product, null);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，增加项目失败[%s]" ,e ,product.getName());
		}
		return product;
	}

	public void update(Product product) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，更新项目[%s]" ,product.getName());
		try {
			ZKProductManager.set(product.getName(), product, null);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，更新项目失败[%s]" ,e ,product.getName());
		}
	}

	public void delete(String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，删除项目[%s]" ,productId);
		try {
			ZKProductManager.delete(productId, null);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，删除项目失败[%s]" ,e ,productId);
		}
	}

	public Product get(String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取项目[%s]" ,productId);
		try {
			Product product = ZKProductManager.get(productId, null);
			product.setVersions(versionManager.query(productId));
			return product;
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，获取项目失败[%s]" ,e ,productId);
		}
		return null;
	}

	/**
	 * 模糊匹配
	 * 
	 */
	public List<Product> query(Product product) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取项目[%s]" ,product.getName());
		List<Product> products = new ArrayList<Product>();
		try {
			Map<String, Product> configPathMap = ZKProductManager.getAll(new ConfigPath());
			String productName = product.getName();
			for (Iterator<String> iterator = configPathMap.keySet().iterator(); iterator.hasNext();) {
				String type = iterator.next();
				if (StringUtils.isBlank(productName) || StringUtils.indexOf(type, productName) > -1) {
					products.add(get(type));
				}
			}
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，批量获取项目失败[%s]" ,e ,product.getName());
		}
		return products;
	}

	public List<Product> query(Product product, int start, int limit) {
		return null;
	}

}
