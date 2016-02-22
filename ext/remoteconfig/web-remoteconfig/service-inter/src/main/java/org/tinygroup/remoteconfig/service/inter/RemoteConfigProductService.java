/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.List;

import org.tinygroup.remoteconfig.config.Product;

/**
 * @author Administrator
 *
 */
public interface RemoteConfigProductService {
	
	public void add(Product product);
	
	public void set(Product oldProduct ,Product newProduct);
	
	public void delete(Product product);
	
	public Product get(Product product);
	
	List<Product> query(Product product);
	
}
