package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/9.
 */
public class StringTemplateLoader extends AbstractTemplateLoader<String> {
    public StringTemplateLoader(String type) {
        super(type);
    }

    /**
     * 字符串内存型的，不支持根据路径载入
     *
     * @param path
     * @return
     * @throws TemplateException
     */
    @Override
    protected Template loadTemplate(String path) throws TemplateException {
        return null;
    }

    @Override
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
        Template template = TemplateCompilerUtils.compileTemplate(StringTemplateLoader.class.getClassLoader(), stringTemplateMaterial, getRandomPath());
        //这里没有调用putTemplate是避免内存泄露
        template.setTemplateEngine(getTemplateEngine());
        return template;
    }

    private String getRandomPath() {
        return "/string/template/NoT" + System.nanoTime();
    }

}
