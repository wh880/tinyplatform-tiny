package org.tinygroup.aopcache.processor;

import org.aopalliance.intercept.MethodInvocation;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.base.TemplateRender;
import org.tinygroup.aopcache.exception.AopCacheException;
import org.tinygroup.aopcache.util.TemplateUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.template.TemplateContext;

/**
 * aop缓存删除操作
 * @author renhui
 *
 */
public class AopCacheRemoveProcessor extends AbstractAopCacheProcessor{

	@Override
	public void postProcess(CacheMetadata metadata,
			MethodInvocation invocation, Object result) {
		TemplateRender templateRender=TemplateUtil.getTemplateRender();
		try {
			TemplateContext templateContext=templateRender.assemblyContext(invocation);
			String group=templateRender.renderTemplate(templateContext,metadata.getGroup());
			String removeKeys=templateRender.renderTemplate(templateContext,metadata.getRemoveKeys());
			String removeGroups=templateRender.renderTemplate(templateContext,metadata.getRemoveGroups());
			if(!StringUtil.isBlank(removeKeys)){
				String[] removeArray=removeKeys.split(SPLIT_KEY);
				for (String removeStr : removeArray) {
					getAopCache().remove(group, removeStr);
				}
			}
			if(!StringUtil.isBlank(removeGroups)){
				String[] removeGroupArray = removeGroups.split(SPLIT_KEY);
				for(String removeGroupStr : removeGroupArray){
					getAopCache().cleanGroup(removeGroupStr);
				}
			}
			
		} catch (Throwable e) {
			throw new AopCacheException(e);
		} 
	}
}
