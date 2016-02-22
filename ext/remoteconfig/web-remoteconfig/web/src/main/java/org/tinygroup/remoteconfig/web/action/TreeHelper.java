/**
 * 
 */
package org.tinygroup.remoteconfig.web.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.service.DefaultEnvironment;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigItemService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigProductService;
import org.tinygroup.remoteconfig.service.utils.PathHelper;

/**
 * @author yanwj
 *
 */
public class TreeHelper {

	protected static RemoteConfigProductService produceService;
	
	protected static RemoteConfigItemService remoteConfigItemService;
	
	/**
	 * APP树
	 * 
	 * @param appName
	 * @param treeLists
	 */
	public static void getAppTree(String appName ,List<Map<String ,String>> treeLists){
		Product product = new Product();
		product.setName(appName);
		product = produceService.get(product);
		
		if (product != null) {
			String productId = NodeCache.createNodeId(appName, null);
			treeLists.add(createTree(productId, appName, "0", true, false ,true));
			for (Version version : product.getVersions()) {
				String versionNode = PathHelper.getConfigPath(appName ,version.getName());
				String versionId = NodeCache.createNodeId(versionNode, null);
				treeLists.add(createTree(versionId, version.getVersion(), productId, true, true ,true));
				getVersionTree(version, versionId, treeLists);
			}
		}
	}
	
	/**
	 * 版本树
	 * 
	 * @param versionNode
	 * @param pid
	 * @param treeLists
	 */
	private static void getVersionTree(Version version ,String pid ,List<Map<String ,String>> treeLists){
		Environment defaultEnv = null;
		//先处理初始环境
		for (Environment environment : version.getEnvironment()) {
			if (StringUtils.equals(environment.getName(), IRemoteConfigConstant.DEFAULT_ENV)) {
				defaultEnv = environment;
				String nodeId = NodeCache.createNodeId(environment.getName() ,pid);
				treeLists.add(createTree(nodeId, environment.getEnvironment(), pid, true, true ,false));
				getDefaultModuleTree(environment,nodeId, treeLists);
				break;
			}
		}
		//其次处理其他环境
		if (defaultEnv != null) {
			for (Environment environment : version.getEnvironment()) {
				String nodeId = NodeCache.createNodeId(environment.getName() ,pid);
				treeLists.add(createTree(nodeId, environment.getEnvironment(), pid, false, false ,false));
				if (!StringUtils.equals(environment.getName(), IRemoteConfigConstant.DEFAULT_ENV)) {
					getModuleTree(environment,defaultEnv ,nodeId, treeLists);
				}
			}
		}
	}
	
	/**
	 * 环境模块树
	 * 基础环境
	 * 
	 * @param moduleNode
	 * @param pid
	 * @param treeLists
	 */
	private static void getDefaultModuleTree(Environment environment ,String pid ,List<Map<String ,String>> treeLists){
		for (Module module : environment.getModules()) {
			String moduleId = NodeCache.createNodeId(module.getName(), pid);
			getDefaultModuleTree(module, moduleId, true, treeLists);
		}
	}
	
	private static void getDefaultModuleTree(Module module ,String pid , boolean isEdit ,List<Map<String ,String>> treeLists){
		List<Module> subModules = module.getSubModules();
		for (Module subModule : subModules) {
			String nodeId = NodeCache.createNodeId(subModule.getName(), pid);
			boolean isAdd = false;
			Map<String , String> tempNodeMap = remoteConfigItemService.getAll(PathHelper.createConfigPath(NodeCache.getNodeById(nodeId)));
			if (tempNodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
				isAdd = isAdd(tempNodeMap, NodeCache.getNodeById(pid));
			}else {
				continue;
			}
			treeLists.add(createTree(nodeId, module.getModuleName(), pid, isAdd, true ,false));
			getDefaultModuleTree(subModule, nodeId, isEdit, treeLists);
		}
	}
	
	private static void getModuleTree(Module module ,String pid , boolean isEdit ,List<Map<String ,String>> treeLists){
		List<Module> subModules = module.getSubModules();
		for (Module subModule : subModules) {
			String nodeId = NodeCache.createNodeId(subModule.getName(), pid);
			treeLists.add(createTree(nodeId, module.getModuleName(), pid, false, false ,false));
			getModuleTree(subModule, nodeId, isEdit, treeLists);
		}
	}
	
	/**
	 * 环境模块树
	 * 附属环境
	 * 
	 * @param moduleNode
	 * @param pid
	 * @param treeLists
	 */
	private static void getModuleTree(Environment environment ,Environment defaultEnv ,String pid ,List<Map<String ,String>> treeLists){
		for (Module module : defaultEnv.getModules()) {
			String moduleId = NodeCache.createNodeId(module.getName(), pid);
			getModuleTree(module, moduleId, false, treeLists);
		}
	}
	
	/**
	 * 获取附属模块所对应的基础模块对象
	 * 
	 * @param modulePath
	 * @return
	 */
	public static ConfigPath getDefaultEnvServiceItem(String moduleNode){
		ConfigPath configPath = PathHelper.createConfigPath(moduleNode);
		if (StringUtils.isNotBlank(configPath.getEnvironmentName())) {
			configPath.setEnvironmentName(DefaultEnvironment.DEFAULT.getName());
		}else {
			return null;
		}
		return configPath;
	}
	
	/**
	 * 判断当前是否允许新增操作
	 * 
	 * @param nodeMap
	 * @param parentNode
	 * @return
	 */
	public static boolean isAdd(Map<String , String> nodeMap ,String parentNode){
		//TODO:长度判断不规范，后续修改
		if (StringUtils.split(parentNode ,"/").length <= 3) {
			return true;
		}
		if (StringUtils.indexOf(parentNode, IRemoteConfigConstant.DEFAULT_ENV) == -1) {
			return false;
		}
		for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (StringUtils.equals(key, IRemoteConfigConstant.MODULE_FLAG)) {
				continue;
			}
			
			Map<String , String> tempNodeMap = remoteConfigItemService.getAll(PathHelper.createConfigPath(PathHelper.getConfigPath(parentNode ,key)));
			if (!isModule(tempNodeMap)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 拼装树结构
	 * 
	 * @param id 当前id
	 * @param value 值
	 * @param parentId 父节点id
	 * @param isAdd 是否允许新增
	 * @param isEdit 是否允许编辑，删除
	 * @param isOpen 是否打开
	 * @return
	 */
	private static Map<String ,String> createTree(String id,String value , String parentId ,boolean isAdd ,boolean isEdit ,boolean isOpen){
		Map<String ,String> itemMap = new HashMap<String, String>();
		itemMap.put("id", id);
		itemMap.put("name", value);
		itemMap.put("add", isAdd?"true":"");
		itemMap.put("edit", isEdit?"true":"");
		itemMap.put("open", isOpen?"true":"");
		if (NodeCache.isExitById(parentId)) {
			itemMap.put("pId", parentId);
		}
		return itemMap;
	}
	
	/**
	 * 判断当日的树节点是否是模块
	 * 
	 * @param nodeMap
	 * @return
	 */
	protected static boolean isModule(Map<String ,String> nodeMap){
		if (nodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
