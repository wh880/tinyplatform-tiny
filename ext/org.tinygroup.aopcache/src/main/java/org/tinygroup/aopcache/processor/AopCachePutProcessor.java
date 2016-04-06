package org.tinygroup.aopcache.processor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeanWrapper;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.base.TemplateRender;
import org.tinygroup.aopcache.exception.AopCacheException;
import org.tinygroup.aopcache.util.TemplateUtil;
import org.tinygroup.beanwrapper.BeanWrapperHolder;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.template.TemplateContext;

import java.beans.PropertyDescriptor;

/**
 * aop缓存存放操作
 *
 * @author renhui
 */
public class AopCachePutProcessor extends AbstractAopCacheProcessor {

    @Override
    public void postProcess(CacheMetadata metadata,
                            MethodInvocation invocation, Object result) {
        TemplateRender templateRender = TemplateUtil
                .getTemplateRender();
        try {
            TemplateContext templateContext = templateRender.assemblyContext(invocation);
            String group = templateRender.renderTemplate(templateContext, metadata.getGroup());
            String keys = templateRender.renderTemplate(templateContext, metadata.getKeys());
            String removeKeys = templateRender.renderTemplate(templateContext, metadata
                    .getRemoveKeys());
            String removeGroup = templateRender.renderTemplate(templateContext, metadata
                    .getRemoveGroups());
            String parameterNames = metadata.getParameterNames();
            // 先做删除
            if (!StringUtil.isBlank(removeKeys)) {
                String[] removeArray = removeKeys.split(SPLIT_KEY);
                for (String removeStr : removeArray) {
                    getAopCache().remove(group, removeStr);
                }
            }
            if (!StringUtil.isBlank(removeGroup)) {
                String[] removeGroupArray = removeGroup.split(SPLIT_KEY);
                for (String removeGroupStr : removeGroupArray) {
                    getAopCache().cleanGroup(removeGroupStr);
                }
            }
//			 long expire=metadata.getExpire();
            if (StringUtil.isBlank(parameterNames)) {//不缓存参数，那么缓存方法返回值,以key列表第一个key为缓存的key
                if (result != null) {
                    getAopCache().put(group, keys.split(SPLIT_KEY)[0], result);
                }
            } else {
                String[] keyArray = keys.split(SPLIT_KEY);
                String[] namesArray = parameterNames.split(SPLIT_KEY);
                Assert.assertTrue(keyArray.length == namesArray.length,
                        "方法参数名称和缓存的key个数要相同");
                for (int i = 0; i < keyArray.length; i++) {
                    //从上下文迭代取出参数对应参数值作为value
                    Object value = templateRender.getParamValue(templateContext, namesArray[i]);
                    if (value != null) {
                        //标记为合并
                        if(metadata.isMerge()){
                            Object cacheValue = getAopCache().get(group,keyArray[i]);
                            BeanWrapper valueWrapper = BeanWrapperHolder.getInstance()
                                    .getBeanWrapper(value);
                            BeanWrapper cacheValueWrapper = BeanWrapperHolder.getInstance()
                                    .getBeanWrapper(cacheValue);
                            PropertyDescriptor[] cachePropertyDescriptors = cacheValueWrapper.getPropertyDescriptors();
                            for(PropertyDescriptor cachepd : cachePropertyDescriptors){
                                if(cacheValueWrapper.isWritableProperty(cachepd.getName())){
                                    Object updateobj = valueWrapper
                                            .getPropertyValue(cachepd.getName());
                                    if(updateobj!=null){
                                        cacheValueWrapper.setPropertyValue(cachepd.getName(),updateobj);
                                    }
                                }
                            }
                            getAopCache().put(group, keyArray[i], cacheValue);//放入合并后的值
                        }else {
                            getAopCache().put(group, keyArray[i], value);
                        }
                    }
                }
            }

        } catch (Throwable e) {
            throw new AopCacheException(e);
        }
    }


    protected void checkMetadata(CacheMetadata metadata) {
        Assert.hasText(metadata.getKeys(), "keys不能为空");
    }


}
