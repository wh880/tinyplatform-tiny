package org.tinygroup.template.interpret.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.loader.AbstractResourceLoader;

/**
 * Created by luog on 15/7/20.
 */
public class InterpretResourceLoader extends AbstractResourceLoader<String>{
    public InterpretResourceLoader(String templateExtName, String layoutExtName, String macroLibraryExtName) {
        super(templateExtName, layoutExtName, macroLibraryExtName);
    }

    @Override
    protected Template loadTemplateItem(String path) throws TemplateException {
        return null;
    }

    public String getResourceContent(String path, String encode) throws TemplateException {
        return null;
    }

    public Template createTemplate(String templateMaterial) throws TemplateException {
        return null;
    }
}
