package org.tinygroup.remoteconfig.manager;

import java.util.List;

import org.tinygroup.remoteconfig.config.Product;

public interface ProductManager {
	Product add(Product product);

	void update(Product product);

	void delete(String productId);

	Product get(String productId);
	
	List<Product> query(Product product);
	
	
	List<Product> query(Product product,int start,int limit);
}
