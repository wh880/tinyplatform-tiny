package org.tinygroup.template.application;

import java.util.List;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.I18nVisitor;
import org.tinygroup.template.ResourceLoader;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateFunction;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 解析处理模板引擎的基本属性和第三方函数、处理器的扩展
 * @author yancheng11334
 *
 */
public class TinyTemplateConfigProcessor extends AbstractApplicationProcessor{

	private static final Logger LOGGER = LoggerFactory.getLogger(TinyTemplateConfigProcessor.class);
	
	private static final String RESOURCE_CONFIG_NAME = "resource-loader";
    private static final String INIT_PARAM_NAME = "init-param";
    private static final String I18N_VISITOR_NAME = "i18n-visitor";
    private static final String TEMPLATE_FUNCTION_NAME = "template-function";
    private static final String STATIC_CLASS_NAME = "static-class";
    
	private XmlNode applicationConfig;
	private XmlNode componentConfig;
	private TemplateEngine templateEngine;
	
	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void start() {
		LOGGER.logMessage(LogLevel.INFO, "开始加载Tiny模板引擎基本配置");
		//合并节点
        XmlNode totalConfig = ConfigurationUtil.combineXmlNode(applicationConfig, componentConfig);

        //设置模板引擎的参数
        configEngineProperties(totalConfig);
         
        //设置国际化访问接口
        configI18nVisitor(totalConfig);
         
        //加载资源加载器
        addResourceLoaders(totalConfig);
         
        //加载模板函数
        addFunction(totalConfig);
        
        //加载自定义的静态类
        addStaticClass(totalConfig);
         
		LOGGER.logMessage(LogLevel.INFO, "加载Tiny模板引擎基本配置结束");
	}
	
	private void configEngineProperties(XmlNode totalConfig){
		 
		 //根据DEBUG_MODE设置checkModified，但是如果用户设置checkModified属性会被覆盖
	     String debug_mode = ConfigurationUtil.getConfigurationManager().getConfiguration("DEBUG_MODE");
	     if(!StringUtil.isBlank(debug_mode)){
	    	 templateEngine.setCheckModified(Boolean.parseBoolean(debug_mode));
	    	 LOGGER.logMessage(LogLevel.INFO, "根据DEBUG_MODE设置模板引擎参数checkModified={0}",templateEngine.isCheckModified());
	     }
	     
	        
    	List<XmlNode> list = totalConfig.getSubNodes(INIT_PARAM_NAME);
    	if(list!=null){
    		for(XmlNode node:list){
        		try{
        			String name = node.getAttribute("name");
        			String value = node.getAttribute("value");
        			
        			if("encode".equalsIgnoreCase(name)){
        				templateEngine.setEncode(StringUtil.defaultIfBlank(value, "UTF-8"));
        				LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数encode={0}",templateEngine.getEncode());
        			}else if("safeVariable".equalsIgnoreCase(name)){
        				templateEngine.setSafeVariable(Boolean.parseBoolean(StringUtil.defaultIfBlank(value, "false")));
        				LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数safeVariable={0}",templateEngine.isSafeVariable());
        			}else if("compactMode".equalsIgnoreCase(name)){
        				templateEngine.setCompactMode(Boolean.parseBoolean(StringUtil.defaultIfBlank(value, "false")));
        				LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数compactMode={0}",templateEngine.isCompactMode());
        			}else if("checkModified".equalsIgnoreCase(name)){
        				templateEngine.setCheckModified(Boolean.parseBoolean(StringUtil.defaultIfBlank(value, "false")));
        				LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数checkModified={0}",templateEngine.isCheckModified());
        			}else if("localeTemplateEnable".equalsIgnoreCase(name)){
        				templateEngine.setLocaleTemplateEnable(Boolean.parseBoolean(StringUtil.defaultIfBlank(value, "false")));
        				LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数localeTemplateEnable={0}",templateEngine.isLocaleTemplateEnable());
        			}else if("layoutNames".equalsIgnoreCase(name)){
        	            if(!StringUtil.isBlank(value) && templateEngine instanceof TemplateEngineDefault){
        	               TemplateEngineDefault defaultEngine = (TemplateEngineDefault) templateEngine;
        	               defaultEngine.registerLayoutNames(value.split(","));
        	               LOGGER.logMessage(LogLevel.INFO, "设置模板引擎参数layoutNames={0}",value);
        	            }
        			}
        		}catch (Exception e) {
        			LOGGER.errorMessage("设置模板引擎属性[{0}]出错,属性值[{1}]", e ,node.getAttribute("name"),node.getAttribute("value"));
    			}
        	}
    	}
    	
    }
	
	private void addFunction(XmlNode totalConfig){
    	List<XmlNode> list = totalConfig.getSubNodes(TEMPLATE_FUNCTION_NAME);
    	if(list!=null){
    		for(XmlNode node:list){
        		try {
        			TemplateFunction function = createFunction(node);
        			templateEngine.addTemplateFunction(function);
    			} catch (Exception e) {
    				LOGGER.errorMessage("加载模板引擎的函数出错", e);
    			}
        	}
    	}
    	
    }
	
	private void addStaticClass(XmlNode totalConfig){
		List<XmlNode> list = totalConfig.getSubNodes(STATIC_CLASS_NAME);
		if(list!=null){
			for(XmlNode node:list){
				try {
    				templateEngine.registerStaticClassOperator(new XmlNodeStaticClassOperator(node));
    			} catch (Exception e) {
    				LOGGER.errorMessage("加载用户注册的静态类出错", e);
    			}
			}
		}
	}
	
	private void configI18nVisitor(XmlNode totalConfig){
    	XmlNode node = totalConfig.getSubNode(I18N_VISITOR_NAME);
    	try {
    		I18nVisitor i18n = createI18nVisitor(node);
    		templateEngine.setI18nVisitor(i18n);
		} catch (Exception e) {
			LOGGER.errorMessage("加载模板引擎的国际化资源访问器出错", e);
		}
    }
	
	private I18nVisitor createI18nVisitor(XmlNode node) throws Exception{
    	if(node==null){
 		   return null;
 		}
    	String beanName = node.getAttribute("name");
    	LOGGER.logMessage(LogLevel.INFO, "正在加载模板引擎国际化资源访问器[{0}]",beanName);
    	ClassLoader loader = this.getClass().getClassLoader();
    	return BeanContainerFactory.getBeanContainer(loader).getBean(beanName);
    }
	
	 private ResourceLoader<?> createResourceLoader(XmlNode node) throws Exception{
	    	if(node==null){
	  		   return null;
	  		}
	    	String beanName = node.getAttribute("name");
	    	LOGGER.logMessage(LogLevel.INFO, "正在加载模板引擎用户扩展的资源加载器[{0}]",beanName);
	    	ClassLoader loader = this.getClass().getClassLoader();
	    	return BeanContainerFactory.getBeanContainer(loader).getBean(beanName);
	 }
	 
	 private TemplateFunction createFunction(XmlNode node) throws Exception{
	    	if(node==null){
	 		   return null;
	 		}
	    	String beanName = node.getAttribute("name");
	    	LOGGER.logMessage(LogLevel.INFO, "正在加载模板引擎的函数[{0}]",beanName);
	    	ClassLoader loader = this.getClass().getClassLoader();
	    	return BeanContainerFactory.getBeanContainer(loader).getBean(beanName);
	    }
	
	 private void addResourceLoaders(XmlNode totalConfig) {
	        
	        //用户扩展的第三方资源加载器
	        List<XmlNode> list = totalConfig.getSubNodes(RESOURCE_CONFIG_NAME);
	        if(list!=null){
	        	for(XmlNode node:list){
	            	try {
	    				ResourceLoader<?> loader = createResourceLoader(node);
	    				templateEngine.addResourceLoader(loader);
	    			} catch (Exception e) {
	    				LOGGER.errorMessage("加载用户扩展的资源加载器出错", e);
	    			}
	            }
	        }
	        
	    }

	public void stop() {
		
	}

	public String getApplicationNodePath() {
		return "/application/template-config";
	}

	public String getComponentConfigPath() {
		return "/templateconfig.config.xml";
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
	    this.applicationConfig = applicationConfig;
	    this.componentConfig = componentConfig;
	}

	public XmlNode getComponentConfig() {
		return componentConfig;
	}

	public XmlNode getApplicationConfig() {
		return applicationConfig;
	}

	public int getOrder() {
		return 0;
	}

}
