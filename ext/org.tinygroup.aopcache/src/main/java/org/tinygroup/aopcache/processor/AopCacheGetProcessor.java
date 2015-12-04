package org.tinygroup.aopcache.processor;

import org.aopalliance.intercept.MethodInvocation;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.base.TemplateRender;
import org.tinygroup.aopcache.exception.AopCacheException;
import org.tinygroup.aopcache.util.TemplateUtil;
import org.tinygroup.template.TemplateContext;


/**
 * 缓存获取操作
 * @author renhui
 *
 */
public class AopCacheGetProcessor extends AbstractAopCacheProcessor {

	@Override
	public boolean doPreProcess(CacheMetadata metadata,
			MethodInvocation invocation) {
		return false;
	}

	@Override
	public Object endProcessor(CacheMetadata metadata,
			MethodInvocation invocation) {
		
		TemplateRender templateRender=TemplateUtil.getTemplateRender();
		try {
			TemplateContext templateContext=templateRender.assemblyContext(invocation);
			String group=templateRender.renderTemplate(templateContext,metadata.getGroup());
			String keys=templateRender.renderTemplate(templateContext,metadata.getKeys());
			String[] keyArray=keys.split(SPLIT_KEY);
			Object result = null;
			for (String key : keyArray) {
				result=getAopCache().get(group, key);
				if(result!=null){
					return result;
				}
			}
			if(result==null){
			    result=invocation.proceed();
			    if(result!=null){
			    	for (String key : keyArray) {
			    		getAopCache().put(group, key, result);
					}
			    }
			}
			return result;
		} catch (Throwable e) {
			throw new AopCacheException(e);
		} 
	}

	

}
