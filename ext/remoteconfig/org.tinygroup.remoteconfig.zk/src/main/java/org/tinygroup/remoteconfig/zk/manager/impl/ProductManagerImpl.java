/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.manager.ProductManager;
import org.tinygroup.remoteconfig.manager.VersionManager;

/**
 * @author yanwj06282
 *
 */
public class ProductManagerImpl extends BaseManager implements ProductManager {
	
	VersionManager versionManager;
	
	public void setVersionManager(VersionManager versionManager) {
		this.versionManager = versionManager;
	}
	
	public Product add(Product product) {
		configItemManager.set(product.getName(), product.getTitle(), null);
		return product;
	}

	public void update(Product product) {
		configItemManager.set(product.getName(), product.getTitle(), null);
	}

	public void delete(String productId) {
		configItemManager.delete(productId, null);

	}

	public Product get(String productId) {
		String value = configItemManager.get(productId, null);
		Product product = new Product();
		product.setName(productId);
		product.setTitle(value);
		product.setVersions(versionManager.query(productId));
		return product;
	}

	/**
	 * 模糊匹配
	 * 
	 */
	public List<Product> query(Product product) {
		List<Product> products = new ArrayList<Product>();
		Map<String, String> configPathMap = configItemManager.getALL(new ConfigPath());
		String productName = product.getName();
		for (Iterator<String> iterator = configPathMap.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			if (StringUtils.isBlank(productName) || StringUtils.indexOf(type, productName) > -1) {
				products.add(get(type));
			}
		}
		return products;
	}

	public List<Product> query(Product product, int start, int limit) {
		return null;
	}

}
