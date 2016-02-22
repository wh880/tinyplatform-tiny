/**
 * 
 */
package org.tinygroup.remoteconfig.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.service.utils.PathHelper;

/**
 * 因为没有数据库，而前端树组件需要ID进行组装，所以这个类负责节点的缓存id组装
 * 同时缓存节点和ID的对应关系，就相当于一个内存数据库了。。。
 * 
 * @author yanwj
 *
 */
public class NodeCache {

	/**
	 * 考虑到一些系统保留节点，id计算从10开始，依次+1
	 * 
	 */
	private static int maxId = 1;
	/**
	 * id的缓存
	 * key为id
	 * value为nodeid
	 */
	private static Map<String ,String> idCache = new HashMap<String ,String>();
	/**
	 * node的缓存
	 * key为value
	 * value为id
	 */
	private static Map<String ,String> nodeCache = new HashMap<String ,String>();
	
	/**
	 * 内存中构建id，id要保证唯一，否则数据会错乱
	 * 
	 * @param node
	 * @return id
	 */
	public static String createNodeId(String node,String parentId){
		if (StringUtils.isNotBlank(parentId)) {
			node = StringUtils.isBlank(idCache.get(parentId))?node:PathHelper.getConfigPath(idCache.get(parentId) ,node);
		}
		if (StringUtils.isNotBlank(node) && !StringUtils.startsWith(node, "/")) {
			node = "/".concat(node);
		}
		String value = getIdByNode(node);
		if (value != null) {
			return value;
		}
		int id = maxId;
		String idStr = String.valueOf(id);
		idCache.put(idStr ,node);
		nodeCache.put(node, idStr);
		maxId++;
		return getIdByNode(node);
	}
	
	public static void deleteNodeById(String id){
		try {
			nodeCache.remove(idCache.get(id));
			idCache.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteNodeByNode(String node){
		try {
			idCache.remove(nodeCache.get(node));
			nodeCache.remove(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateIdCache(String id , String value){
		String oldNode = idCache.get(id);
		if (oldNode != null) {
			nodeCache.remove(oldNode);
			nodeCache.put(value, id);
			idCache.put(id, value);
		}
	}
	
	public static Map<String ,String> getIdCache(){
		return idCache;
	}
	
	public static Map<String ,String> getNodeCache(){
		return nodeCache;
	}
	
	public static String getNodeById(String id){
		return idCache.get(id);
	}
	
	public static String getIdByNode(String node){
		return nodeCache.get(node)==null ?nodeCache.get("/".concat(node)):nodeCache.get(node);
	}
	
	public static boolean isExitByNode(String node){
		return nodeCache.get(node) != null;
	}
	
	public static boolean isExitById(String id){
		return idCache.get(id) != null;
	}
	
}
