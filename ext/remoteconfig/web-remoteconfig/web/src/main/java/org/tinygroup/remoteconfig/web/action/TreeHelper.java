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
import org.tinygroup.remoteconfig.service.Environment;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigService;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.WebUtils;
import org.tinygroup.remoteconfig.utils.PathHelper;
import org.tinygroup.remoteconfig.web.utils.TreeBlankListUtils;

/**
 * @author yanwj
 *
 */
public class TreeHelper {

	protected static RemoteConfigService remoteConfigService;
	
	/**
	 * APP树
	 * 
	 * @param appNode
	 * @param treeLists
	 */
	public static void getAppTree(String appNode ,List<Map<String ,String>> treeLists){
		Map<String , String> nodeMap = remoteConfigService.getAll(WebUtils.createConfigPath(appNode, ""));
		String pid = NodeCache.getIdByNode(appNode);
		treeLists.add(createTree(pid, appNode, "0", "true", "" ,true));
		for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String value = nodeMap.get(key);
			if (TreeBlankListUtils.isAccept(key)) {
				String versionNode = PathHelper.getConfigPath(appNode ,key);
				String versionId = NodeCache.createNodeId(versionNode, null);
				treeLists.add(createTree(versionId, value, pid, "true", "true" ,true));
				getVersionTree(versionNode, versionId, treeLists);
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
	private static void getVersionTree(String versionNode ,String pid ,List<Map<String ,String>> treeLists){
		Map<String , String> nodeMap = remoteConfigService.getAll(WebUtils.createConfigPath(versionNode, ""));
		for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String value = nodeMap.get(key);
			if (TreeBlankListUtils.isAccept(key)) {
				String envNode = PathHelper.getConfigPath(versionNode ,key);
				String nodeId = NodeCache.getIdByNode(envNode);
				String isAdd = "";
				String isEdit = "true";
				if (StringUtils.equals(key, IRemoteConfigConstant.DEFAULT_ENV)) {
					isAdd = "true";
					getDefaultModuleTree(envNode,nodeId, treeLists);
				}else {
					getModuleTree(envNode,nodeId, treeLists);
				}
				treeLists.add(createTree(nodeId, value, pid, isAdd, isEdit ,false));
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
	private static void getDefaultModuleTree(String moduleNode ,String pid ,List<Map<String ,String>> treeLists){
		Map<String , String> nodeMap = remoteConfigService.getAll(WebUtils.createConfigPath(moduleNode, ""));
		//首先判断是否是模块,不是模块直接跳出
		if (nodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
			for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = nodeMap.get(key);
				if (TreeBlankListUtils.isAccept(key)) {
					String subModuleNode = PathHelper.getConfigPath(moduleNode ,key);
					String nodeId = NodeCache.createNodeId(subModuleNode, null);
					String isAdd = "";
					String isEdit = "true";
					Map<String , String> tempNodeMap = remoteConfigService.getAll(WebUtils.createConfigPath(subModuleNode, ""));
					if (tempNodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
						isAdd = isAdd(tempNodeMap, subModuleNode);
					}else {
						continue;
					}
					treeLists.add(createTree(nodeId, value, pid, isAdd, isEdit ,false));
					//递归
					getDefaultModuleTree(subModuleNode,nodeId, treeLists);
				}
			}
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
	private static void getModuleTree(String moduleNode ,String pid ,List<Map<String ,String>> treeLists){
		//自身模块，包括重写公共模块的属性
		ConfigServiceItem serviceItem = WebUtils.createConfigPath(moduleNode, "");
		//公共模块
		ConfigServiceItem defaultServiceItem = getDefaultEnvServiceItem(moduleNode);
		Map<String , String> nodeMap = remoteConfigService.getAll(serviceItem);
		if (defaultServiceItem == null) {
			return;
		}
		Map<String , String> defaultNodeMap = remoteConfigService.getAll(defaultServiceItem);
		//覆盖
		defaultNodeMap.putAll(nodeMap);
		//首先判断是否是模块,不是模块直接跳出
		if (defaultNodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
			for (Iterator<String> iterator = defaultNodeMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = defaultNodeMap.get(key);
				if (TreeBlankListUtils.isAccept(key)) {
					String subModuleNode = PathHelper.getConfigPath(moduleNode ,key);
					String nodeId = NodeCache.createNodeId(subModuleNode ,null);
 					ConfigServiceItem tempDefaultServiceItem = getDefaultEnvServiceItem(subModuleNode);
					Map<String , String> tempNodeMap = remoteConfigService.getAll(tempDefaultServiceItem);
					if (tempNodeMap.get(IRemoteConfigConstant.MODULE_FLAG) == null) {
						continue;
					}
					if (StringUtils.isBlank(value)) {
						value = remoteConfigService.get(tempDefaultServiceItem);
					}
					treeLists.add(createTree(nodeId, value, pid, "", "" ,false));
					//递归
					getModuleTree(subModuleNode,nodeId, treeLists);
				}
			}
		}
	}
	
	/**
	 * 获取附属模块所对应的基础模块对象
	 * 
	 * @param modulePath
	 * @return
	 */
	public static ConfigServiceItem getDefaultEnvServiceItem(String moduleNode){
		ConfigServiceItem defaultServiceItem = WebUtils.createConfigPath(moduleNode, "");
		if (defaultServiceItem.getConfigPath() != null && StringUtils.isNotBlank(defaultServiceItem.getConfigPath().getEnvironmentName())) {
			defaultServiceItem.getConfigPath().setEnvironmentName(Environment.DEFAULT.getName());
		}else {
			return null;
		}
		return defaultServiceItem;
	}
	
	/**
	 * 判断当前是否允许新增操作
	 * 
	 * @param nodeMap
	 * @param parentNode
	 * @return
	 */
	public static String isAdd(Map<String , String> nodeMap ,String parentNode){
		if (StringUtils.split(parentNode ,"/").length <= 3) {
			return "true";
		}
		if (StringUtils.indexOf(parentNode, IRemoteConfigConstant.DEFAULT_ENV) == -1) {
			return "";
		}
		for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (StringUtils.equals(key, IRemoteConfigConstant.MODULE_FLAG)) {
				continue;
			}
			Map<String , String> tempNodeMap = remoteConfigService.getAll(WebUtils.createConfigPath(PathHelper.getConfigPath(parentNode ,key), ""));
			if (!isModule(tempNodeMap)) {
				return "";
			}
		}
		return "true";
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
	private static Map<String ,String> createTree(String id,String value , String parentId ,String isAdd ,String isEdit ,boolean isOpen){
		Map<String ,String> itemMap = new HashMap<String, String>();
		itemMap.put("id", id);
		itemMap.put("name", value);
		itemMap.put("add", String.valueOf(isAdd));
		itemMap.put("edit", String.valueOf(isEdit));
		itemMap.put("open", String.valueOf(isOpen));
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
