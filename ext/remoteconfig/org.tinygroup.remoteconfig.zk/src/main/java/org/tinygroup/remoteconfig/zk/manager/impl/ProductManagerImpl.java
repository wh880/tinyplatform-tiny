/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.List;

import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.manager.ProductManager;
import org.tinygroup.remoteconfig.manager.VersionManager;

/**
 * @author yanwj06282
 *
 */
public class ProductManagerImpl extends BaseManager implements ProductManager {
	
	VersionManager versionManager;
	
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

	public List<Product> query(Product product) {
		return null;
	}

	public List<Product> query(Product product, int start, int limit) {
		return null;
	}

}
