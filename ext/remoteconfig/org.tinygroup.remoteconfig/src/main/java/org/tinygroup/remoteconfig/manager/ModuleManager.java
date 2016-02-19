package org.tinygroup.remoteconfig.manager;

import java.util.List;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;

public interface ModuleManager {
	/**
	 * 添加一个module，若entity的中moduleid为空，则直接在环境下添加module，否则作为module的子module
	 * @param module
	 * @param entity
	 * @return
	 */
	Module add(Module module, ConfigPath entity);

	/**
	 * 更新一个module
	 * @param module
	 * @param entity
	 */
	void update(Module module, ConfigPath entity);

	/**
	 * 删除一个module
	 * @param entity
	 */
	void delete(ConfigPath entity);

	/**
	 * 查询一个module
	 * @param entity
	 * @return
	 */
	Module get(ConfigPath entity);


	/**
	 * 查询一个module下所有的子module
	 * @param entity
	 * @return
	 */
	public List<Module> querySubModules(ConfigPath entity);
}
