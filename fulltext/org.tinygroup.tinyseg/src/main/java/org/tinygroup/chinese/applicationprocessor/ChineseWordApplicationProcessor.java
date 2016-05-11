package org.tinygroup.chinese.applicationprocessor;

import java.util.List;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.chinese.fileProcessor.ChineseContainer;
import org.tinygroup.xmlparser.node.XmlNode;

public class ChineseWordApplicationProcessor extends AbstractApplicationProcessor {
	private static final String FILE_CHINESE_NODE_PATH = "/application/chinese";
	private static final String SCENE_TAG = "scene";
	private XmlNode appConfig;

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		appConfig = applicationConfig;
	}

	public XmlNode getApplicationConfig() {
		return appConfig;
	}

	public String getApplicationNodePath() {
		return FILE_CHINESE_NODE_PATH;
	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public int getOrder() {
		return 0;
	}

	public void init() {
		if(appConfig==null){
			return;
		}
		List<XmlNode> scenes = appConfig.getSubNodesRecursively(SCENE_TAG);
		for(XmlNode scene:scenes){
			String sceneName = scene.getAttribute("name");
			String type = scene.getAttribute("type");
			String wordNames = scene.getAttribute("words");
			ChineseContainer.RegScene(sceneName, wordNames, type);
		}
	}


	public void start() {
		

	}

	public void stop() {
		

	}

}
