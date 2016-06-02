/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.impl;

import org.tinygroup.commons.tools.ExceptionUtil;
import org.tinygroup.template.*;
import org.tinygroup.template.application.*;
import org.tinygroup.template.function.*;
import org.tinygroup.template.interpret.MacroException;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.interpret.context.*;
import org.tinygroup.template.interpret.terminal.*;
import org.tinygroup.template.rumtime.TemplateUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineDefault implements TemplateEngine {

    private static final String DEFAULT = "default";
    private Map<String, TemplateFunction> functionMap = new HashMap<String, TemplateFunction>();
    private Map<Class, Map<String, TemplateFunction>> typeFunctionMap = new HashMap<Class, Map<String, TemplateFunction>>();
    private TemplateContext templateEngineContext;

    private List<ResourceLoader> resourceLoaderList = new ArrayList<ResourceLoader>();
    private String encode = "UTF-8";
    private I18nVisitor i18nVisitor;
    private TemplateCache<String, List<Template>> layoutPathListCache = new TemplateCacheDefault<String, List<Template>>();
    private TemplateCache<String, Macro> macroCache = new TemplateCacheDefault<String, Macro>();
    private TemplateCache<String, Template> templateCache = new TemplateCacheDefault<String, Template>();
    private List<String> macroLibraryList = new ArrayList<String>();
    private Map<String, Template> repositories = new ConcurrentHashMap<String, Template>();
    private TemplateCache<String,Object> localeSearchResults = new TemplateCacheDefault<String,Object>();
    private boolean checkModified = false;
    private boolean localeTemplateEnable = false;
   
    public boolean isLocaleTemplateEnable() {
		return localeTemplateEnable;
	}

	public void setLocaleTemplateEnable(boolean localeTemplateEnable) {
		this.localeTemplateEnable = localeTemplateEnable;
	}

	public void setCheckModified(boolean checkModified) {
        this.checkModified = checkModified;
    }

    public boolean isCheckModified() {
        return checkModified;
    }

    public Map<String, Template> getRepositories() {
        return repositories;
    }

    public void setRepositories(Map<String, Template> repositories) {
        this.repositories = repositories;
    }

    public static final TemplateInterpreter interpreter = new TemplateInterpreter();

    //提供模板引擎渲染的静态类
    private static Map<String, StaticClassOperator> staticClassOperatorMap = new HashMap<String, StaticClassOperator>();

    static {
        interpreter.addTerminalNodeProcessor(new IntegerOctNodeProcessor());
        interpreter.addTerminalNodeProcessor(new EscapeTextNodeProcessor());
        interpreter.addTerminalNodeProcessor(new FalseNodeProcessor());
        interpreter.addTerminalNodeProcessor(new IntegerNodeProcessor());
        interpreter.addTerminalNodeProcessor(new NullNodeProcessor());
        interpreter.addTerminalNodeProcessor(new StringDoubleNodeProcessor());
        interpreter.addTerminalNodeProcessor(new StringSingleNodeProcessor());
        interpreter.addTerminalNodeProcessor(new TextCdataNodeProcessor());
        interpreter.addTerminalNodeProcessor(new TextPlainNodeProcessor());
        interpreter.addTerminalNodeProcessor(new TrueNodeProcessor());
        interpreter.addTerminalNodeProcessor(new FloatProcessor());
        interpreter.addTerminalNodeProcessor(new IntegerHexNodeProcessor());
    }

    static {
        interpreter.addContextProcessor(new PageContentProcessor());
        interpreter.addContextProcessor(new MapProcessor());
        interpreter.addContextProcessor(new ExpressionGroupProcessor());
        interpreter.addContextProcessor(new ValueProcessor());
        interpreter.addContextProcessor(new ForProcessor());
        interpreter.addContextProcessor(new SetProcessor());
        interpreter.addContextProcessor(new IfProcessor());
        interpreter.addContextProcessor(new ElseIfProcessor());
        interpreter.addContextProcessor(new RangeProcessor());
        interpreter.addContextProcessor(new ArrayProcessor());
        interpreter.addContextProcessor(new MathBinaryProcessor());
        interpreter.addContextProcessor(new MathCompareProcessor());
        interpreter.addContextProcessor(new MathSingleRightProcessor());
        interpreter.addContextProcessor(new MathSingleLeftProcessor());
        interpreter.addContextProcessor(new BlankProcessor());
        interpreter.addContextProcessor(new TabProcessor());
        interpreter.addContextProcessor(new EolProcessor());
        interpreter.addContextProcessor(new CommentProcessor());
        interpreter.addContextProcessor(new MathIdentifierProcessor());
        interpreter.addContextProcessor(new ForBreakProcessor());
        interpreter.addContextProcessor(new ForContinueProcessor());
        interpreter.addContextProcessor(new MapListProcessor());
        interpreter.addContextProcessor(new MathUnaryProcessor());
        interpreter.addContextProcessor(new MathConditionProcessor());
        interpreter.addContextProcessor(new MathConditionSimpleProcessor());
        interpreter.addContextProcessor(new MathCompareConditionProcessor());
        interpreter.addContextProcessor(new MathCompareRalationProcessor());
        interpreter.addContextProcessor(new MathBinaryShiftProcessor());
        interpreter.addContextProcessor(new MathBitwiseProcessor());
        interpreter.addContextProcessor(new ArrayGetProcessor());
        interpreter.addContextProcessor(new ImportIgnoreProcessor());
        interpreter.addContextProcessor(new MacroDefineIgnoreProcessor());
        interpreter.addContextProcessor(new CallProcessor());
        interpreter.addContextProcessor(new CallWithBodyProcessor());
        interpreter.addContextProcessor(new CallMacroProcessor());
        interpreter.addContextProcessor(new CallMacroWithBodyProcessor());
        interpreter.addContextProcessor(new LayoutDefineProcessor());
        interpreter.addContextProcessor(new LayoutImplementProcessor());
        interpreter.addContextProcessor(new DentProcessor());
        interpreter.addContextProcessor(new IndentProcessor());
        interpreter.addContextProcessor(new TextProcessor());
        interpreter.addContextProcessor(new BodyContentProcessor());
        interpreter.addContextProcessor(new FieldProcessor());
        interpreter.addContextProcessor(new StopProcessor());
        interpreter.addContextProcessor(new ReturnProcessor());
        interpreter.addContextProcessor(new IncludeProcessor());
        interpreter.addContextProcessor(new MemberFunctionCallProcessor());
        interpreter.addContextProcessor(new FunctionCallProcessor());
        interpreter.addContextProcessor(new WhileProcessor());
    }

    static {
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Integer", Integer.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Long", Long.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Short", Short.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Double", Double.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Float", Float.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Boolean", Boolean.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("String", String.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Byte", Byte.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Number", Number.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("Math", Math.class));
        addDefaultStaticClassOperator(new DefaultStaticClassOperator("System", System.class));
    }

    private static void addDefaultStaticClassOperator(StaticClassOperator operator) {
        staticClassOperatorMap.put(operator.getName(), operator);
    }

    private boolean compactMode;

    public boolean isSafeVariable() {
        return TemplateUtil.isSafeVariable();
    }

    public TemplateInterpreter getTemplateInterpreter() {
        return interpreter;
    }

    public void setSafeVariable(boolean safeVariable) {
        TemplateUtil.setSafeVariable(safeVariable);
    }

    public boolean isCompactMode() {
        return compactMode;
    }

    public void setCompactMode(boolean compactMode) {
        this.compactMode = compactMode;
    }


    public void registerMacroLibrary(String path) throws TemplateException {
        registerMacroLibrary(getMacroLibrary(path));
    }


    public void registerMacro(Macro macro) throws TemplateException {
        macroCache.put(macro.getName(), macro);
    }

    public void registerMacroLibrary(Template Template) throws TemplateException {
        for (Map.Entry<String, Macro> entry : Template.getMacroMap().entrySet()) {
        	Macro macro = entry.getValue();
        	if(macro.getMacroPath()==null){
         	   macro.setMacroPath(Template.getPath());
         	}
            registerMacro(entry.getValue());
        }
    }

    public void write(OutputStream outputStream, Object data) throws TemplateException {
        try {
            if (data != null) {
                if (data instanceof byte[]) {
                    outputStream.write((byte[]) data);
                } else if (data instanceof ByteArrayOutputStream) {
                    outputStream.write(((ByteArrayOutputStream) data).toByteArray());
                } else {
                    outputStream.write(data.toString().getBytes(getEncode()));
                }
            }
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }


    public TemplateEngineDefault() {
        //添加一个默认的加载器
        addTemplateFunction(new FormatterTemplateFunction());
        addTemplateFunction(new InstanceOfTemplateFunction());
        addTemplateFunction(new GetResourceContentFunction());
        addTemplateFunction(new EvaluateTemplateFunction());
        addTemplateFunction(new CallMacroFunction());
        addTemplateFunction(new GetFunction());
        addTemplateFunction(new RandomFunction());
        addTemplateFunction(new ToIntFunction());
        addTemplateFunction(new ToLongFunction());
        addTemplateFunction(new ToBoolFunction());
        addTemplateFunction(new ToFloatFunction());
        addTemplateFunction(new ToDoubleFunction());
        addTemplateFunction(new FormatDateFunction());
        addTemplateFunction(new TodayFunction());
        addTemplateFunction(new ParseTemplateFunction());
        addTemplateFunction(new I18nFunction());
        addTemplateFunction(new ExtendMapFunction());
        addTemplateFunction(new RenderLayerFunction());
    }

    public TemplateContext getTemplateContext() {
        if (templateEngineContext == null) {
            templateEngineContext = new TemplateContextDefault(staticClassOperatorMap);
        }
        return templateEngineContext;
    }

    public TemplateEngineDefault setEncode(String encode) {
        this.encode = encode;
        return this;
    }


    public TemplateEngineDefault setI18nVisitor(I18nVisitor i18nVistor) {
        this.i18nVisitor = i18nVistor;
        return this;
    }


    public I18nVisitor getI18nVisitor() {
        return i18nVisitor;
    }

    public void setResourceLoaderList(List<ResourceLoader> resourceLoaderList) {
        this.resourceLoaderList = resourceLoaderList;
    }

    public TemplateEngineDefault addTemplateFunction(TemplateFunction function) {
        function.setTemplateEngine(this);
        String[] names = function.getNames().split(",");
        if (function.getBindingTypes() == null) {
            for (String name : names) {
                functionMap.put(name, function);
            }
        } else {
            String[] types = function.getBindingTypes().split(",");
            for (String type : types) {
                try {
                    Class clazz = Class.forName(type);
                    Map<String, TemplateFunction> nameMap = typeFunctionMap.get(clazz);
                    if (nameMap == null) {
                        nameMap = new HashMap<String, TemplateFunction>();
                        typeFunctionMap.put(clazz, nameMap);
                    }
                    for (String name : names) {
                        nameMap.put(name, function);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }


    public TemplateFunction getTemplateFunction(String methodName) {
        return functionMap.get(methodName);
    }


    public TemplateFunction getTemplateFunction(Object object, String methodName) {
        Map<String, TemplateFunction> typeMap = typeFunctionMap.get(object.getClass());
        if (typeMap != null) {
            TemplateFunction function = typeMap.get(methodName);
            if (function != null) {
                return function;
            }
        }
        for (Class clz : typeFunctionMap.keySet()) {
            if (clz.isInstance(object)) {
                TemplateFunction function = typeFunctionMap.get(clz).get(methodName);
                if (function != null) {
                    return function;
                }
            }
        }
        return null;
    }

    public String getEncode() {
        return encode;
    }


    public TemplateEngineDefault addResourceLoader(ResourceLoader resourceLoader) {
        resourceLoader.setTemplateEngine(this);
        resourceLoaderList.add(resourceLoader);
        return this;
    }

    private Template findTemplate(TemplateContext context, String path) throws TemplateException {
    	if(localeTemplateEnable){
    		//查询国际化模板
    		Locale locale = context.get("defaultLocale");
            if (locale != null) {
                String localePath = TemplateUtil.getLocalePath(path, locale);
                if(localeSearchResults.contains(localePath)){
             	   return findTemplate(path);
             	}
                try{
                	Template template = findTemplate(localePath);
                	if(template!=null){
                	   return template;
                	}else{
                		localeSearchResults.put(localePath, "");
                	}
                }catch(TemplateException e){
                   //findTemplate查找不到国际化模板资源可能会抛出异常，这时候再找默认模板资源
                   localeSearchResults.put(localePath, "");
                   return findTemplate(path);
                }
            }
    	}
        //查询默认模板
        return findTemplate(path);
    }

    private Template findLayout(TemplateContext context, String path) throws TemplateException {
    	if(localeTemplateEnable){
    		//查询国际化模板
    		Locale locale = context.get("defaultLocale");
            if (locale != null) {
            	String localePath = TemplateUtil.getLocalePath(path, locale);
            	if(localeSearchResults.contains(localePath)){
            	   return findLayout(path,false);
            	}
                try{
                	Template template = findLayout(localePath,false);
                	if(template!=null){
                	   return template;
                	}else{
                		localeSearchResults.put(localePath, "");
                	}
                }catch(TemplateException e){
                   //findLayout查找不到国际化模板资源可能会抛出异常，这时候再找默认模板资源
                   localeSearchResults.put(localePath, "");
                   return findLayout(path,false);
                }
            }
    	}
    	//查询原始模板
        return findLayout(path,false);
    }

    public Template findTemplate(String path) throws TemplateException {
        Template template = null;
        if (!checkModified) {
            template = repositories.get(path);
            if (template != null) {
                return template;
            }
        }
        if (template == null) {
            for (ResourceLoader loader : resourceLoaderList) {
                template = loader.getTemplate(path);
                if (template != null) {
                    templateCache.put(path, template);
                    return template;
                }
            }
        }
        throw new TemplateException("找不到模板：" + path);
    }

    public Template findLayout(String path) throws TemplateException {
        return findLayout(path,true);
    }
    
    private Template findLayout(String path,boolean throwException) throws TemplateException {
        Template template = null;
        if (!checkModified) {
            template = repositories.get(path);
            if (template != null) {
                return template;
            }
        }
        if (template == null) {
            for (ResourceLoader loader : resourceLoaderList) {
                template = loader.getLayout(path);
                if (template != null) {
                    templateCache.put(path, template);
                    return template;
                }
            }
        }
        if(throwException){
        	throw new TemplateException("找不到模板：" + path);
        }else{
        	return null;
        }
    }

    private Template getMacroLibrary(String path) throws TemplateException {
        Template template = null;
        if (!checkModified) {
            template = repositories.get(path);
            if (template != null) {
                return template;
            }
        }
        for (ResourceLoader loader : resourceLoaderList) {
            template = loader.getMacroLibrary(path);
            if (template != null) {
                repositories.put(path, template);
                return template;
            }
        }
        throw new TemplateException("找不到模板：" + path);
    }

    public TemplateEngineDefault put(String key, Object value) {
        templateEngineContext.put(key, value);
        return this;
    }


    public void renderMacro(String macroName, Template Template, TemplateContext context, OutputStream outputStream) throws TemplateException {
        findMacro(macroName, Template, context).render(Template, context, context, outputStream);
    }


    public void renderMacro(Macro macro, Template Template, TemplateContext context, OutputStream outputStream) throws TemplateException {
        macro.render(Template, context, context, outputStream);
    }

    public void renderTemplate(String path, TemplateContext context, OutputStream outputStream) throws TemplateException {
        try {
            Template template = findTemplate(context, path);
            if (template != null) {
            	//先执行page的渲染
            	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                template.render(context, byteArrayOutputStream);
            	
                List<Template> layoutPaths = getLayoutList(context,template.getPath());
                if (layoutPaths.size() > 0) {
                    context.put("pageContent", byteArrayOutputStream);
                    ByteArrayOutputStream layoutWriter = null;
                   
                    TemplateContext layoutContext = context;
                    for (int i = layoutPaths.size() - 1; i >= 0; i--) {
                        //每次都构建新的Writer和Context来执行
                        TemplateContext tempContext = new TemplateContextDefault();
                        tempContext.setParent(layoutContext);
                        layoutContext = tempContext;
                        layoutWriter = new ByteArrayOutputStream();
                        layoutPaths.get(i).render(layoutContext, layoutWriter);
                        if (i > 0) {
                            layoutContext.put("pageContent", layoutWriter);
                        }
                    }
                    outputStream.write(layoutWriter.toByteArray());
                } else {
                    //renderTemplate(template, context, outputStream);
                	outputStream.write(byteArrayOutputStream.toByteArray());
                }
            } else {
                throw new TemplateException("找不到模板：" + path);
            }
        } catch (IOException e) {
            throw new TemplateException(e);
        } catch(TemplateException e){
        	TemplateException te = processTemplateException(e);
        	throw te;
   	    }
    }

    public void renderTemplateWithOutLayout(String path, TemplateContext context, OutputStream outputStream) throws TemplateException {
        Template template = findTemplate(context, path);
        if (template != null) {
            renderTemplate(template, context, outputStream);
        } else {
            throw new TemplateException("找不到模板：" + path);
        }
    }

    private List<Template> getLayoutList(TemplateContext context, String templatePath) throws TemplateException {
        List<Template> layoutPathList = null;
        Integer renderLayer = context.get("$renderLayer", 0);//取得用户设置的布局渲染次数
        
        String cacheKey =  templatePath+"|"+renderLayer.toString(); //重新定义缓存的key值 
        if (!checkModified) {
            layoutPathList = layoutPathListCache.get(cacheKey);
            if (layoutPathList != null) {
                return layoutPathList;
            }
        }
        if (layoutPathList == null) {
            layoutPathList = new ArrayList<Template>();
        }
        String[] paths = templatePath.split("/");
        String path = "";

        String templateFileName = paths[paths.length - 1];
        int num = renderLayer;
        if(num<=0 || num >= paths.length - 1){
           num =  paths.length - 1;  //用户设置的渲染次数越界，重置为实际值
        }
        for (int i = paths.length-1-num; i < paths.length - 1; i++) {
            path += paths[i] + "/";
            String template = path + templateFileName;
            Template layout = null;
            //先找同名的看有没有
            for (ResourceLoader loader : resourceLoaderList) {
                String layoutPath = loader.getLayoutPath(template);
                if (layoutPath != null) {
                    layout = findLayout(context, layoutPath);
                    if (layout != null) {
                        layoutPathList.add(layout);
                        break;
                    }
                }
            }
            //如果没有找到,则看看默认的有没有
            if (layout == null) {
                for (ResourceLoader loader : resourceLoaderList) {
                    String layoutPath = loader.getLayoutPath(template);
                    if (layoutPath != null) {
                        String defaultTemplateName = path + DEFAULT + layoutPath.substring(layoutPath.lastIndexOf('.'));
                        layout = findLayout(context, defaultTemplateName);
                        if (layout != null) {
                            layoutPathList.add(layout);
                            break;
                        }
                    }
                }
            }
        }
        if (!checkModified) {
            layoutPathListCache.put(cacheKey, layoutPathList);
        }

        return layoutPathList;
    }


    public void renderTemplate(String path) throws TemplateException {
        renderTemplate(path, new TemplateContextDefault(), System.out);
    }


    public void renderTemplate(Template Template) throws TemplateException {
        renderTemplate(Template, new TemplateContextDefault(), System.out);
    }


    public void renderTemplate(Template template, TemplateContext context, OutputStream outputStream) throws TemplateException {
    	try{
    		 template.render(context, outputStream);
    	}catch(TemplateException e){
    		TemplateException te = processTemplateException(e);
        	throw te;
    	}
    }

    public Macro findMacro(Object macroNameObject, Template template, TemplateContext context) throws TemplateException {
        //上下文中的宏优先处理，主要是考虑bodyContent宏
        String macroName = macroNameObject.toString();
        Object obj = context.getItemMap().get(macroName);
        if (obj instanceof Macro) {
            return (Macro) obj;
        }
        //查找私有宏
        Macro macro = template.getMacroMap().get(macroName);
        if (macro != null) {
            return macro;
        }
        //先查找import的列表，后添加的优先
        for (int i = template.getImportPathList().size() - 1; i >= 0; i--) {
            Template macroLibrary = getMacroLibrary(template.getImportPathList().get(i));
            if (macroLibrary != null) {
                macro = macroLibrary.getMacroMap().get(macroName);
                if (macro != null) {
                	if(macro.getMacroPath()==null){
                	   macro.setMacroPath(macroLibrary.getPath());
                	}
                    if (!checkModified) {
                        macroCache.put(macroName, macro);
                    }
                    return macro;
                }
            }
        }

        macro = macroCache.get(macroName);
        if (macro != null) {
            return macro;
        }

        /**
         * 查找公共宏，后添加的优先
         */
        for (int i = macroLibraryList.size() - 1; i >= 0; i--) {
            String path = macroLibraryList.get(i);
            if (!template.getImportPathList().contains(path)) {
                Template macroLibrary = getMacroLibrary(path);
                if (macroLibrary != null) {
                    macro = macroLibrary.getMacroMap().get(macroName);
                    if (macro != null) {
                    	if(macro.getMacroPath()==null){
                     	   macro.setMacroPath(macroLibrary.getPath());
                     	}
                        if (!checkModified) {
                            macroCache.put(macroName, macro);
                        }
                        return macro;
                    }
                }
            }
        }
        throw new TemplateException("找不到宏：" + macroName);
    }


    public Object executeFunction(Template Template, TemplateContext context, String functionName, Object... parameters) throws TemplateException {
        TemplateFunction function = functionMap.get(functionName);
        if (function != null) {
            return function.execute(Template, context, parameters);
        }
        throw new TemplateException("找不到函数：" + functionName);
    }

    public List<ResourceLoader> getResourceLoaderList() {
        return resourceLoaderList;
    }

    public String getResourceContent(String path, String encode) throws TemplateException {
        for (ResourceLoader resourceLoader : resourceLoaderList) {
            String content = resourceLoader.getResourceContent(path, encode);
            if (content != null) {
                return content;
            }
        }
        throw new TemplateException("找不到资源：" + path);
    }

    public String getResourceContent(String path) throws TemplateException {
        return getResourceContent(path, getEncode());
    }

    public void registerStaticClassOperator(StaticClassOperator operator)
            throws TemplateException {
        staticClassOperatorMap.put(operator.getName(), operator);
    }

    public StaticClassOperator getStaticClassOperator(String name)
            throws TemplateException {
        return staticClassOperatorMap.get(name);
    }
    
    /**
     * 对模板引擎的异常进行处理
     * @param e
     * @return
     */
    private TemplateException processTemplateException(TemplateException e){
    	List<Throwable> list  = ExceptionUtil.getCauses(e,true);
    	if(list!=null){
    		TemplateException te=null;
    		for(Throwable throwable:list){
    		   if(throwable instanceof TemplateException){
    			  if(te==null){
    				 //异常链原始的TemplateException保留
    				 te = (TemplateException) throwable;  
    			  }else{
    				 //异常链上的TemplateException，提取关键信息后抛弃
    				 if(throwable instanceof MacroException){
    					 MacroException me = (MacroException) throwable;
    					 te.addMacro(me.getMacro());
    				 }
    			  }
    		   }
    		}
    		return te;
    	}
    	return e;
    }

}
