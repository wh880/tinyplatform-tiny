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
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigProductService;
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
	protected RemoteConfigProductService remoteConfigProductService;
	
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
			item.setId(NodeCache.createNodeId(item.getKey() ,null));
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
	public String addApp(Product product ,Model model){
		product.setTitle(product.getName());
		remoteConfigProductService.add(product);
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
				Product product = new Product();
				product.setName(node);
				remoteConfigProductService.delete(product);
			}
		}
		return "redirect:/remoteconfig/app/getAllApp";
	}
	
	private List<ConfigViewItem> getAllApp(){
		List<ConfigViewItem> items = new ArrayList<ConfigViewItem>();
		try {
			List<Product> products = remoteConfigProductService.query(new Product());
			for (Product product : products) {
				items.add(new ConfigViewItem(product.getName(), product.getTitle()));
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return items;
	}
	
}
