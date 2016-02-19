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
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigService;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.utils.PathHelper;
import org.tinygroup.remoteconfig.web.action.pojo.ConfigViewItem;


/**
 * @author yanwj
 *
 */
@Controller
@RequestMapping("/remoteconfig/app")
public class RemoteConfigAppAction extends BaseAction{
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigAppAction.class);
	
	@Autowired
	protected RemoteConfigService remoteConfigServiceImplWrapper;
	
	/**
	 * 获取指定项目的配置项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkApp")
	public Map<String ,String> checkNode(String param ,Model model){
		if (NodeCache.getIdByNode(param) != null) {
			return createAjaxReponse("", "该项目已经存在！");
		}else {
			return createAjaxReponse("y", "通过");
		}
	}
	
	/**
	 * 获取所有的APP
	 * 
	 * @return
	 */
	@RequestMapping("/getAllApp")
	public String getAllApp(Model model){
		List<ConfigViewItem> items = getAllApp();
		for (ConfigViewItem item : items) {
			item.setId(NodeCache.getIdByNode(item.getKey()));
		}
		model.addAttribute("items", items);
		if (items.size() > 0) {
			model.addAttribute("defaultApp" ,items.get(0));
		}
		return "/page/toggleboxNew.page";
	}

	/**
	 * 增加App
	 */
	@RequestMapping("/addApp")
	public String addApp(ConfigViewItem item ,Model model){
		item.setValue(item.getKey());
		ConfigServiceItem serviceItem = tranPojo(item ,new ConfigPath());
		remoteConfigServiceImplWrapper.add(serviceItem);
		setDefaultModelFalg(serviceItem);
		return "redirect:/remoteconfig/app/getAllApp";
	}
	
	/**
	 * 删除App
	 */
	@RequestMapping("/deleteAppPage")
	public String deleteAppPage(Model model){
		model.addAttribute("items", getAllApp());
		return "/app/AppDelete.pagelet";
	}
	
	/**
	 * 删除App
	 */
	@RequestMapping("/deleteApp")
	public String deleteApp(String myCheckBox ,Model model){
		String[] appIds = StringUtils.split(myCheckBox, ",");
		if (appIds != null && appIds.length > 0) {
			for (String node : appIds) {
				remoteConfigServiceImplWrapper.delete(new ConfigServiceItem(node, "", null));
			}
		}
		return "redirect:/remoteconfig/app/getAllApp";
	}
	
	private List<ConfigViewItem> getAllApp(){
		List<ConfigViewItem> items = new ArrayList<ConfigViewItem>();
		try {
			Map<String ,String> itemMap = remoteConfigServiceImplWrapper.getAll(new ConfigServiceItem("" ,"" ,null));
			for (Iterator<String> iterator = itemMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = itemMap.get(key);
				items.add(new ConfigViewItem(key, value));
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return items;
	}
	
	/**
	 * 设置默认模块表示，用以区分模块和非模块
	 * 
	 * @param serviceItem
	 */
	protected void setDefaultModelFalg(ConfigServiceItem serviceItem){
		serviceItem.setNode(PathHelper.getConfigPath(serviceItem.getNode() ,IRemoteConfigConstant.MODULE_FLAG));
		serviceItem.setValue(IRemoteConfigConstant.MODULE_FLAG);
		remoteConfigServiceImplWrapper.add(serviceItem);
	}
	
}
