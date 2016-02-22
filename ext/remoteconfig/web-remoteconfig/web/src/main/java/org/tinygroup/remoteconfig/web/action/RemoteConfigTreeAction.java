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
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.config.Product;
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigEnvService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigItemService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigModuleService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigProductService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigVersionService;
import org.tinygroup.remoteconfig.service.utils.PathHelper;
import org.tinygroup.remoteconfig.web.utils.AddBlankListUtils;

/**
 * 这个树的结构是这样的
 * 
 * app1 app2 version rd key1(value1) key2(value2) ... rd2 key1(value1)
 * key2(value2) ... version2 rd key1(value1) key2(value2) ... ... app3 ...
 * 
 * @author yanwj
 * 
 */
@Controller
@RequestMapping("/remoteconfig/nodetree")
public class RemoteConfigTreeAction extends BaseAction {

	@Autowired
	protected RemoteConfigProductService remoteConfigProductService;
	@Autowired
	protected RemoteConfigVersionService remoteConfigVersionService;
	@Autowired
	RemoteConfigModuleService remoteConfigModuleService;
	@Autowired
	RemoteConfigEnvService remoteConfigEnvironmentService;
	@Autowired
	protected RemoteConfigItemService remoteConfigItemService;

	/**
	 * 
	 * 
	 * @param app
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTree")
	public List<Map<String, String>> getTree(String id, String appId,
			Model model) {
		List<Map<String, String>> treeLists = new ArrayList<Map<String, String>>();
		Product defaultProduct = null;
		if (StringUtils.isBlank(appId) || NodeCache.getNodeById(appId) == null) {
			List<Product> productList = remoteConfigProductService
					.query(new Product());
			if (productList.size() > 0) {
				defaultProduct = productList.get(0);
			} else {
				return treeLists;
			}
		} else {
			defaultProduct = new Product();
			defaultProduct.setName(NodeCache.getNodeById(appId));
			defaultProduct = remoteConfigProductService.get(defaultProduct);
		}
		if (defaultProduct != null) {
			TreeHelper.produceService = remoteConfigProductService;
			TreeHelper.remoteConfigItemService = remoteConfigItemService;
			TreeHelper.getAppTree(NodeCache.getNodeById(appId), treeLists);
			fillDefaultContent(id, model);
		}
		return treeLists;
	}

	/**
	 * 判断是增加版本/环境/模块
	 * 
	 * @param pid
	 * @param name
	 * @param selectApp
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addTreeNode")
	public Map<String, String> addTreeNode(String pid, String name,
			String selectApp, Model model) {
		if (StringUtils.isBlank(pid) || StringUtils.isBlank(name)
				|| !AddBlankListUtils.isAccept(name)) {
			return createAjaxReponse("", "添加失败！");
		}
		String parentNode = NodeCache.getNodeById(pid);
		// 验证是否允许增加
		if (!TreeHelper.isAdd(remoteConfigItemService.getAll(PathHelper
				.createConfigPath(parentNode)), parentNode)) {
			return createAjaxReponse("", "叶子模块无法添加子模块！只允许操作K&V");
		}
		if (StringUtils.isNotBlank(parentNode)) {
			String[] nodes = StringUtils.split(parentNode, "/");
			try {
				switch (nodes.length) {
				case 1:
					// 1是增加版本
					Version version = new Version();
					version.setName(name);
					version.setVersion(name);
					remoteConfigVersionService.add(version, parentNode);
					break;
				case 2:
					// 2是增加环境
					Environment environment = new Environment();
					environment.setName(name);
					environment.setEnvironment(name);
					remoteConfigEnvironmentService.add(environment, nodes[1], nodes[0]);
					break;
				default:
					//超过2都是增加模块
					String path = PathHelper.getConfigPath(parentNode, name);
					ConfigPath configPath = PathHelper.createConfigPath(path);
					Module module = new Module();
					module.setName(name);
					module.setModuleName(name);
					remoteConfigModuleService.add(module, configPath);
					break;
				}
				return response(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return createAjaxReponse("", "添加失败！");
	}

	@ResponseBody
	@RequestMapping("/deleteTreeNode")
	public Map<String, String> deleteTreeNode(String id, Model model) {
		String node = NodeCache.getNodeById(id);
		String[] nodes = StringUtils.split(node, "/");
		try {
			switch (nodes.length) {
			case 1:
				//1是项目
				break;
			case 2:
				// 2是版本
				Version version = new Version();
				version.setName(nodes[1]);
				remoteConfigVersionService.delete(version, nodes[0]);
				break;
			case 3:
				// 3是环境
				Environment environment = new Environment();
				environment.setName(nodes[2]);
				remoteConfigEnvironmentService.delete(environment, nodes[1], nodes[0]);
				break;
			default:
				//超过3都是模块
				ConfigPath configPath = PathHelper.createConfigPath(node);
				remoteConfigModuleService.delete(configPath);
				break;
			}
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response(false);
	}

	@ResponseBody
	@RequestMapping("/editTreeNode")
	public Map<String, String> editTreeNode(String id, String name, Model model) {
		String node = NodeCache.getNodeById(id);
		String[] nodes = StringUtils.split(node, "/");
		try {
			switch (nodes.length) {
			case 1:
				//1是项目
				break;
			case 2:
				// 2是版本
				Version oldVersion = new Version();
				oldVersion.setName(nodes[1]);
				Version newVersion = new Version();
				newVersion.setName(name);
				oldVersion = remoteConfigVersionService.get(oldVersion, nodes[0]);
				remoteConfigVersionService.set(oldVersion ,newVersion, nodes[0]);
				break;
			case 3:
				// 3是环境
				Environment oldEnvironment = new Environment();
				oldEnvironment.setName(nodes[2]);
				Environment newEnvironment = new Environment();
				newEnvironment.setName(name);
				remoteConfigEnvironmentService.set(oldEnvironment ,newEnvironment, nodes[1], nodes[0]);
				break;
			default:
				//超过3都是模块
				Module oldModule = new Module();
				Module newModule = new Module();
				newModule.setModuleName(name);
				ConfigPath configPath = PathHelper.createConfigPath(node);
				remoteConfigModuleService.set(oldModule ,newModule ,configPath);
				break;
			}
			return response(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response(false);
	}

}
