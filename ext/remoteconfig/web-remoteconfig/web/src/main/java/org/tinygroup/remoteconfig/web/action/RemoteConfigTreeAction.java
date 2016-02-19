/**
 * 
 */
package org.tinygroup.remoteconfig.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigService;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.WebUtils;
import org.tinygroup.remoteconfig.web.utils.AddBlankListUtils;

/**
 * 这个树的结构是这样的
 * 
 * app1
 * app2
 * 		version
 * 			rd
 * 				key1(value1)
 * 				key2(value2)
 * 				...
 * 			rd2
 * 				key1(value1)
 * 				key2(value2)
 * 				...
 * 		version2
 * 			rd
 * 				key1(value1)
 * 				key2(value2)
 * 				...
 * 		...
 * app3
 * ...
 * 
 * @author yanwj
 *
 */
@Controller
@RequestMapping("/remoteconfig/nodetree")
public class RemoteConfigTreeAction extends BaseAction{

	@Autowired
	protected RemoteConfigService remoteConfigTreeServiceImplWrapper;
	
	/**
	 * 
	 * 
	 * @param app
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTree")
	public List<Map<String ,String>> getTree(String id ,String appId ,Model model){
		List<Map<String ,String>> treeLists = new ArrayList<Map<String,String>>();
		String appNode = "";
		if (StringUtils.isBlank(appId) || NodeCache.getNodeById(appId) == null) {
			Map<String ,String> itemMap = remoteConfigTreeServiceImplWrapper.getAll(new ConfigServiceItem("" ,"" ,null));
			if (itemMap.size() > 0) {
				appNode = itemMap.keySet().toArray(new String[0])[0];
			}else {
				return treeLists;
			}
		}else {
			appNode = NodeCache.getNodeById(appId);
		}
		if (StringUtils.isNotBlank(appNode)) {
			TreeHelper.remoteConfigService = remoteConfigTreeServiceImplWrapper;
			TreeHelper.getAppTree(appNode, treeLists);
			fillDefaultContent(id, model);
		}
		return treeLists;
	}
	
	@ResponseBody
	@RequestMapping("/addTreeNode")
	public Map<String ,String> addTreeNode(String pid ,String name ,String selectApp ,Model model){
		if (StringUtils.isBlank(pid) || StringUtils.isBlank(name) || !AddBlankListUtils.isAccept(name)) {
			return createAjaxReponse("", "添加失败！");
		}
		String parentNode = NodeCache.getNodeById(pid);
		//验证是否允许增加
		if (!StringUtils.equals(TreeHelper.isAdd(remoteConfigTreeServiceImplWrapper.getAll(WebUtils.createConfigPath(parentNode, "")), parentNode), "true")) {
			return createAjaxReponse("", "叶子模块无法添加子模块！只允许操作K&V");
		}
		if (StringUtils.isNotBlank(parentNode)) {
			String nodeName = parentNode.concat("/").concat(name);
			ConfigServiceItem serviceItem = WebUtils.createConfigPath(nodeName, name);
			if (remoteConfigTreeServiceImplWrapper.isExit(serviceItem)) {
				return createAjaxReponse("", "该节点已存在！");
			}
			try {
				remoteConfigTreeServiceImplWrapper.add(serviceItem);
				return response(true);
			} catch (Exception e) {
				e.printStackTrace();
				return response(false);
			}
		}
		return createAjaxReponse("", "添加失败！");
	}
	
	@ResponseBody
	@RequestMapping("/deleteTreeNode")
	public Map<String ,String> deleteTreeNode(String id ,Model model){
		String node = NodeCache.getNodeById(id);
		try {
			//删除当前模块
			remoteConfigTreeServiceImplWrapper.delete(WebUtils.createConfigPath(node, ""));
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
			return response(false);
		}
	}
	
	@ResponseBody
	@RequestMapping("/editTreeNode")
	public Map<String , String> editTreeNode(String id ,String name ,Model model){
		String node = NodeCache.getNodeById(id);
		try {
			remoteConfigTreeServiceImplWrapper.set(id ,WebUtils.createConfigPath(node, name));
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
			return response(false);
			
		}
	}
	
}
