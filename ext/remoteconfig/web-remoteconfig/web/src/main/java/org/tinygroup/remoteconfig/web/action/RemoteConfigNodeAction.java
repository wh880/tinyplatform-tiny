/**
 * 
 */
package org.tinygroup.remoteconfig.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.service.Environment;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigService;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.EnvironmentHelper;
import org.tinygroup.remoteconfig.service.utils.WebUtils;
import org.tinygroup.remoteconfig.utils.PathHelper;
import org.tinygroup.remoteconfig.web.action.pojo.ConfigViewItem;
import org.tinygroup.remoteconfig.web.utils.AddBlankListUtils;

/**
 * @author yanwj
 *
 */
@Controller
@RequestMapping("/remoteconfig/node")
public class RemoteConfigNodeAction extends BaseAction{

	@Autowired
	protected RemoteConfigService remoteConfigNodeServiceImplWrapper;
	
	/**
	 * 获取所有的配置项
	 * 
	 * @return
	 */
	@RequestMapping("/getAllItem")
	public String getAllItem(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		List<ConfigViewItem> items = null;
		if (StringUtils.isBlank(id)) {
			items = new ArrayList<ConfigViewItem>();
		}else {
			try {
				Map<String ,String> nodeMap = remoteConfigNodeServiceImplWrapper.getAll(WebUtils.createConfigPath(node, ""));
				Map<String ,String> defaultNodeMap = remoteConfigNodeServiceImplWrapper.getAll(TreeHelper.getDefaultEnvServiceItem(node));
				items = tranPojos(nodeMap , defaultNodeMap ,id);
				defaultNodeMap.putAll(nodeMap);
				boolean isAdd = isAdd(defaultNodeMap, node);
				if (StringUtils.indexOf(node, IRemoteConfigConstant.DEFAULT_ENV)>-1) {
					if (isAdd) {
						model.addAttribute(IS_ADD, true);
					}
					model.addAttribute(IS_DELETE, true);
					model.addAttribute(IS_EDIT, true);
				}else if (isAdd) {
					model.addAttribute(IS_EDIT, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("items", items);
		fillDefaultContent(id, model);
		model.addAttribute("node", node);
		return "/data/tinytabledata.pagelet";
	}
	
	public boolean isAdd(Map<String ,String> nodeMap ,String parentNode){
		for (Iterator<String> iterator = nodeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String path = PathHelper.getConfigPath(parentNode ,key);
			Map<String ,String> tempNodeMap = remoteConfigNodeServiceImplWrapper.getAll(TreeHelper.getDefaultEnvServiceItem(path));
			if (isModule(tempNodeMap)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取所有的配置项
	 * 
	 * @return
	 */
	@RequestMapping("/getAllItemBySubId")
	public String getAllItemBySubId(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		List<ConfigViewItem> items = null;
		if (StringUtils.isBlank(id) || StringUtils.isBlank(node)) {
			items = new ArrayList<ConfigViewItem>();
		}else {
			String parentNode = StringUtils.substringBeforeLast(node, "/");
			try {
				Map<String ,String>  serviceItemMap = remoteConfigNodeServiceImplWrapper.getAll(WebUtils.createConfigPath(parentNode, ""));
				Map<String ,String>  defaultServiceItemMap = remoteConfigNodeServiceImplWrapper.getAll(TreeHelper.getDefaultEnvServiceItem(parentNode));
				items = tranPojos(serviceItemMap ,defaultServiceItemMap ,NodeCache.getIdByNode(parentNode));
				model.addAttribute("id", NodeCache.getIdByNode(parentNode));
				defaultServiceItemMap.putAll(serviceItemMap);
				boolean isAdd = isAdd(serviceItemMap, parentNode);
				if (StringUtils.indexOf(parentNode, IRemoteConfigConstant.DEFAULT_ENV)>-1) {
					if (isAdd) {
						model.addAttribute(IS_ADD, true);
					}
					model.addAttribute(IS_DELETE, true);
					model.addAttribute(IS_EDIT, true);
				}else if (isAdd) {
					model.addAttribute(IS_EDIT, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("node", parentNode);
		}
		model.addAttribute("items", items);
		
		return "/data/tinytabledata.pagelet";
	}
	
	/**
	 * 获取指定配置项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkEditNode")
	public Map<String ,String> checkEditNode(String id ,String param ,Model model){
		String parentNode = NodeCache.getNodeById(id);
		if (StringUtils.endsWith(parentNode, param)) {
			return createAjaxReponse("y", "通过");
		}
		String node = PathHelper.getConfigPath(StringUtils.substringBeforeLast(parentNode, "/") ,param);
		if (NodeCache.getIdByNode(node) != null) {
			return createAjaxReponse("", "该配置已经存在！");
		}else {
			return createAjaxReponse("y", "通过");
		}
	}
	
	/**
	 * 获取指定配置项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkNode")
	public Map<String ,String> checkNode(String id ,String param ,Model model){
		String parentNode = NodeCache.getNodeById(id);
		String node = PathHelper.getConfigPath(parentNode ,param);
		if (NodeCache.getIdByNode(node) != null) {
			return createAjaxReponse("", "该配置已经存在！");
		}else {
			return createAjaxReponse("y", "通过");
		}
	}
	
	/**
	 * 获取指定节点的配置项
	 * 
	 * @return
	 */
	@RequestMapping("/getItem")
	public String getItem(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		ConfigViewItem item = new ConfigViewItem(node, remoteConfigNodeServiceImplWrapper.get(WebUtils.createConfigPath(node, "")));
		model.addAttribute("id", id);
		model.addAttribute("item", item);
		return "/data/itemEdit.pagelet";
	}
	
	@RequestMapping("/editItemPage")
	public String editItemPage(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		model.addAttribute("id", id);
		ConfigPath configPath = PathHelper.createConfigPath(node);
		ConfigServiceItem serviceItem = new ConfigServiceItem("", "", configPath);
		String value = remoteConfigNodeServiceImplWrapper.get(serviceItem);
		Map<String, String> itemMap = remoteConfigNodeServiceImplWrapper.getAll(serviceItem);
		if (StringUtils.isBlank(value) && StringUtils.isNotBlank(configPath.getEnvironmentName())) {
			ConfigServiceItem defaultServiceItem = new ConfigServiceItem("", "", EnvironmentHelper.getDefaultEnvPath(configPath));
			value = remoteConfigNodeServiceImplWrapper.get(defaultServiceItem);
		}
		ConfigViewItem item = new ConfigViewItem(StringUtils.substringAfterLast(node, "/") ,value);
		model.addAttribute("item", item);
		if (StringUtils.indexOf(node, IRemoteConfigConstant.DEFAULT_ENV) > -1 && itemMap.get(IRemoteConfigConstant.MODULE_FLAG) == null) {
			model.addAttribute(IS_EDIT, true);
		}
		return "/data/itemEdit.pagelet";
	}
	
	/**
	 * 编辑数据
	 * 这里分两种情况
	 * 如果key变化，则需要删除原先节点，再创建新的
	 * 如果key未变化，则直接覆盖原先节点即可
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editItem")
	public Map<String ,String> editItem(String id ,ConfigViewItem item ,Model model){
		if (!AddBlankListUtils.isAccept(item.getKey())) {
			return response(false);
		}
		String node = NodeCache.getNodeById(id);
		//判断key是否变化
		try {
			if (StringUtils.endsWith(node, "/"+item.getKey())) {
				//key未变化
				remoteConfigNodeServiceImplWrapper.set(id ,WebUtils.createConfigPath(node, item.getValue()));
			}else {
				//key变化
				String parentNode = StringUtils.substringBeforeLast(node, "/");
				remoteConfigNodeServiceImplWrapper.set(id , WebUtils.createConfigPath(PathHelper.getConfigPath(parentNode ,item.getKey()), item.getValue()));
			}
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response(false);
	}
	
	@RequestMapping("/addItemPage")
	public String addItemPage(String id ,Model model){
		model.addAttribute("id", id);
		return "/data/itemAdd.pagelet";
	}
	
	@ResponseBody
	@RequestMapping("/addItem")
	public Map<String ,String> addItem(String id ,ConfigViewItem item ,Model model){
		
		if (!AddBlankListUtils.isAccept(item.getKey())) {
			return response(false);
		}
		String parentNode = NodeCache.getNodeById(id);
		model.addAttribute("id", id);
		if (StringUtils.isNotBlank(parentNode)) {
			try {
				remoteConfigNodeServiceImplWrapper.add(WebUtils.createConfigPath(PathHelper.getConfigPath(parentNode ,item.getKey()), item.getValue()));
				return response(true);
			} catch (Exception e) {
				e.printStackTrace();
				return response(false);
			}
		}
		return response(false);
	}
	
	/**
	 * 删除单条记录
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteNode")
	public Map<String ,String> delete(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		try {
			remoteConfigNodeServiceImplWrapper.delete(WebUtils.createConfigPath(node, ""));
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
			return response(false);
		}
	}
	
	/**
	 * 批量删除记录
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody()
	@RequestMapping("/deleteNodes")
	public Map<String ,String> deletes(String ids ,Model model){
		List<ConfigServiceItem> nodes = new ArrayList<ConfigServiceItem>();
		String[] itemIdss = StringUtils.split(ids, ",");
		for (String id : itemIdss) {
			String node = NodeCache.getNodeById(id);
			if (StringUtils.isNotBlank(node)) {
				nodes.add(WebUtils.createConfigPath(node ,""));
			}
		}
		try {
			remoteConfigNodeServiceImplWrapper.deletes(nodes);
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
			return response(false);
		}
	}
	
}
