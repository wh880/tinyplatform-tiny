/**
 * 
 */
package org.tinygroup.remoteconfig.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.model.RemoteConfig;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.utils.ConfigParseUtils;
import org.tinygroup.remoteconfig.web.action.pojo.ConfigViewItem;
import org.tinygroup.remoteconfig.web.utils.TabBlankListUtils;

/**
 * @author yanwj
 *
 */
public class BaseAction {
	
	protected static final String IS_ADD = "isAdd";
	
	protected static final String IS_EDIT = "isEdit";
	
	protected static final String IS_DELETE = "isDelete";
	
	/**
	 * 对象转化，service层->view层
	 * 
	 * @param item
	 * @return
	 */
	protected ConfigViewItem tranPojo(ConfigServiceItem item){
		return tranPojo(item, "-1");
	}
	
	/**
	 * 对象转化，service层->view层
	 * 
	 * @param item
	 * @return
	 */
	protected ConfigViewItem tranPojo(ConfigServiceItem item ,String parentId){
		if (item == null) {
			return null;
		}
		ConfigViewItem viewItem = new ConfigViewItem();
		viewItem.setId(NodeCache.getIdByNode(item.getKey()));
		if (NodeCache.isExitById(parentId)) {
			viewItem.setParentId(parentId);
		}
		viewItem.setKey(item.getKey());
		viewItem.setValue(item.getValue());
		return viewItem;
	}
	
	/**
	 * 批量对象转化
	 * 
	 * @param items
	 * @param parentId 
	 * @return
	 */
	protected List<ConfigViewItem> tranPojos(Map<String ,String> nodeMap , Map<String ,String> defaultNodeMap, String parentId){
		List<ConfigViewItem> viewItems = new ArrayList<ConfigViewItem>();
		if (defaultNodeMap == null) {
			defaultNodeMap = nodeMap;
		}
		Map<String ,String> totleMap = new HashMap<String, String>();
		totleMap.putAll(defaultNodeMap);
		totleMap.putAll(nodeMap);
		for (Iterator<String> iterator = totleMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!TabBlankListUtils.isAccept(key)) {
				continue;
			}
			String value = totleMap.get(key);
			boolean rewrite = false;
			if (defaultNodeMap.containsKey(key) && nodeMap.containsKey(key) && !StringUtils.equals(defaultNodeMap.get(key), nodeMap.get(key))) {
				rewrite = true;
			}
			ConfigViewItem item = new ConfigViewItem(key ,value);
			item.setRewrite(rewrite);
			item.setId(NodeCache.createNodeId(key, parentId));
			item.setParentId(parentId);
			viewItems.add(item);
		}
		return viewItems;
	}
	
	/**
	 * 对象转化，service层->view层
	 * 
	 * @param item
	 * @return
	 */
	protected ConfigServiceItem tranPojo(ConfigViewItem item ,ConfigPath configPath){
		if (item == null) {
			return null;
		}
		
		ConfigServiceItem viewItem = new ConfigServiceItem(item.getKey() ,item.getValue() ,configPath);
		return viewItem;
	}
	
	protected void fillDefaultContent(String id ,Model model){
		if (StringUtils.isBlank(id)) {
			return;
		}
		RemoteConfig config = ConfigParseUtils.createConfig(id);
		if (config != null) {
			model.addAttribute("id", id);
			model.addAttribute("app", config.getApp());
			model.addAttribute("env", config.getEnv());
			model.addAttribute("version", config.getVersion());
		}
	}
	
	
	
	protected Map<String ,String> response(boolean stat){
		if (stat) {
			return createAjaxReponse("y", "操作成功");
		}else {
			return createAjaxReponse("", "操作失败");
		}
	}
	
	protected Map<String ,String> createAjaxReponse(String status , String info){
		Map<String ,String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("info", info);
		return map;
	}
	
	
	protected boolean isModule(Map<String ,String> nodeMap){
		if (nodeMap.get(IRemoteConfigConstant.MODULE_FLAG) != null) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
