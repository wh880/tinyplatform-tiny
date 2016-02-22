/**
 * 
 */
package org.tinygroup.remoteconfig.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.manager.ProductManager;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigProductService;

/**
 * @author yanwj
 *
 */
public class RemoteConfigProductServiceImpl implements RemoteConfigProductService{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigProductServiceImpl.class);
	
	ProductManager productManager;
	
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void add(Product product) {
		try {
			productManager.add(product);
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("增加产品操作失败[%s=%s]", product.getName(), product.getTitle()) ,e);
		}
	}

	/**
	 * 树节点不允许修改key
	 */
	public void set(Product oldProduct ,Product newProduct) {
		try {
			//如果路径发生变化
			if (!StringUtils.equals(oldProduct.getName(), newProduct.getName())) {
				LOGGER.errorMessage(String.format("产品无法修改key[%s=%s]", newProduct.getName(), newProduct.getTitle()));
				return;
			}else {
				//普通修改，key未变化
				productManager.update(newProduct);
			}
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("产品设值操作失败[%s=%s]", newProduct.getName(), newProduct.getTitle()) ,e);
		}
	}

	public Product get(Product product) {
		return productManager.get(product.getName());
	}

	public List<Product> query(Product product){
		return productManager.query(product);
	}

	public void delete(Product product) {
		productManager.delete(product.getName());
	}

	public void deletes(List<Product> products) {
		for (Product product : products) {
			delete(product);
		}
	}
	
}
