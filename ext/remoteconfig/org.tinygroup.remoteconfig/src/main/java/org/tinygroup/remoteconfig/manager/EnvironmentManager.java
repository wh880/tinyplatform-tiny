package org.tinygroup.remoteconfig.manager;

import java.util.List;

import org.tinygroup.remoteconfig.config.Environment;

public interface EnvironmentManager {
	Environment add(Environment env, String versionId, String productId);

	void update(Environment env, String versionId, String productId);

//	void delete(String envId, String versionId, String productId);

	Environment get(String envId, String versionId, String productId);

	/**
	 * 添加一个环境
	 * @param env
	 * @return
	 */
	Environment add(Environment env);
	/**
	 * 修改一个环境
	 * @param env
	 * @return
	 */
	void update(Environment env);
	/**
	 * 删除一个环境
	 * @param env
	 * @return
	 */
	void delete(String envId);
	/**
	 * 查询一个环境
	 * @param env
	 * @return
	 */
	Environment get(String envId);
	/**
	 * 关联一个环境到指定产品版本
	 * @param env
	 * @return
	 */
	void add(String envId, String versionId, String productId);
	/**
	 * 移联环境和指定产品版本的关联
	 * @param env
	 * @return
	 */
	void delete(String envId, String versionId, String productId);
	
	
	List<Environment> query(String versionId, String productId);
	
	
	
	

}
