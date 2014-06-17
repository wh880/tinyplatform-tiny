package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/9.
 */
public class StringResourceLoader extends AbstractResourceLoader<String> {
    public StringResourceLoader() {
        super(null,null,null);
    }

    /**
     * 字符串内存型的，不支持根据路径载入
     *
     * @param path
     * @return
     * @throws TemplateException
     */

    protected Template loadTemplateItem(String path) throws TemplateException {
        return null;
    }


    protected Template loadLayout(String path) throws TemplateException {
        return null;
    }

    protected Template loadMacroLibrary(String path) throws TemplateException {
        return null;
    }

    public boolean isModified(String path) {
        return false;
    }

    /**
     * 字符串内存型的，不支持根据路径载入
     *
     * @param path
     * @param encode
     * @return
     */
    public String getResourceContent(String path, String encode) {
        return null;
    }

    public Template createTemplate(String stringTemplateMaterial) throws TemplateException {
        Template template = ResourceCompilerUtils.compileResource(StringResourceLoader.class.getClassLoader(), stringTemplateMaterial, getRandomPath());
        //这里没有调用putTemplate是避免内存泄露
        template.setTemplateEngine(getTemplateEngine());
        return template;
    }

    public Template createLayout(String templateMaterial) throws TemplateException {
        throw new TemplateException("Not supported.");
    }
    public Template createMacroLibrary(String templateMaterial) throws TemplateException {
        throw new TemplateException("Not supported.");
    }

    private String getRandomPath() {
        return null;
    }
}