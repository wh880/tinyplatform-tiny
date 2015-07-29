/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.springmvc.extension;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.*;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.impl.AbstractCachableExtensionMappingInstanceResolver;
import org.tinygroup.springmvc.extension.impl.MediaTypeRegistrarSupport;

import java.util.Iterator;
import java.util.List;

/**
 * 扩展名对应的Spring mvc 模型对象。
 * <p/>
 * <p/>
 * <strong>注意</strong>：这个是对所有支持的请求url的扩展名的MVC组件抽象。
 *
 */

public class ExtensionMappingInstance implements InitializingBean {
    private static final Logger logger = LoggerFactory
            .getLogger(ExtensionMappingInstance.class);
    /**
     * 扩展名称
     */
    private String extension;
    private String mediaTypes;

    /**
     * MultipartResolver used by this servlet
     */
    private MultipartResolver commonsMultipartResolver;

    /**
     * LocaleResolver used by this servlet
     */
    private LocaleResolver commonLocaleResolver;

    /**
     * ThemeResolver used by this servlet
     */
    private ThemeResolver commonThemeResolver;

    /**
     * List of HandlerMappings used by this servlet
     */
    private List<HandlerMapping> handlerMappings;

    /**
     * List of HandlerAdapters used by this servlet
     */
    private List<HandlerAdapter> handlerAdapters;

    /**
     * List of HandlerExceptionResolvers used by this servlet
     */
    private List<HandlerExceptionResolver> handlerExceptionResolvers;

    /**
     * RequestToViewNameTranslator used by this servlet
     */
    private RequestToViewNameTranslator viewNameTranslator;

    /**
     * List of ViewResolvers used by this servlet
     */
    private List<ViewResolver> viewResolvers;

    private MediaTypeRegistrarSupport mediaTypeRegistrarSupport;
    
    private AbstractCachableExtensionMappingInstanceResolver extensionMappingInstanceResolver;

    // ~~~ bean 方法

    public void setMediaTypeRegistrarSupport(
            MediaTypeRegistrarSupport mediaTypeRegistrarSupport) {
        this.mediaTypeRegistrarSupport = mediaTypeRegistrarSupport;
    }
    
    public void setExtensionMappingInstanceResolver(
			AbstractCachableExtensionMappingInstanceResolver extensionMappingInstanceResolver) {
		this.extensionMappingInstanceResolver = extensionMappingInstanceResolver;
	}

	/**
     * @return Returns the extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension The extension to set.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public void setCommonsMultipartResolver(
			MultipartResolver commonsMultipartResolver) {
		this.commonsMultipartResolver = commonsMultipartResolver;
	}

	public void setCommonLocaleResolver(LocaleResolver commonLocaleResolver) {
		this.commonLocaleResolver = commonLocaleResolver;
	}

	public void setCommonThemeResolver(ThemeResolver commonThemeResolver) {
		this.commonThemeResolver = commonThemeResolver;
	}
	
	public MultipartResolver getCommonsMultipartResolver() {
		return commonsMultipartResolver;
	}

	public LocaleResolver getCommonLocaleResolver() {
		return commonLocaleResolver;
	}

	public ThemeResolver getCommonThemeResolver() {
		return commonThemeResolver;
	}

	/**
     * @return Returns the handlerMappings.
     */
    public List<HandlerMapping> getHandlerMappings() {
        return handlerMappings;
    }

    /**
     * @param handlerMappings The handlerMappings to set.
     */
    public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    /**
     * @return Returns the handlerAdapters.
     */
    public List<HandlerAdapter> getHandlerAdapters() {
        return handlerAdapters;
    }

    /**
     * @param handlerAdapters The handlerAdapters to set.
     */
    public void setHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    /**
     * @return Returns the handlerExceptionResolvers.
     */
    public List<HandlerExceptionResolver> getHandlerExceptionResolvers() {
        return handlerExceptionResolvers;
    }

    /**
     * @param handlerExceptionResolvers The handlerExceptionResolvers to set.
     */
    public void setHandlerExceptionResolvers(
            List<HandlerExceptionResolver> handlerExceptionResolvers) {
        this.handlerExceptionResolvers = handlerExceptionResolvers;
    }

    /**
     * @return Returns the viewNameTranslator.
     */
    public RequestToViewNameTranslator getViewNameTranslator() {
        return viewNameTranslator;
    }

    /**
     * @param viewNameTranslator The viewNameTranslator to set.
     */
    public void setViewNameTranslator(
            RequestToViewNameTranslator viewNameTranslator) {
        this.viewNameTranslator = viewNameTranslator;
    }

    /**
     * @return Returns the viewResolvers.
     */
    public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    /**
     * @param viewResolvers The viewResolvers to set.
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    @Override
    public String toString() {
        StringBuffer sbf = new StringBuffer(143);
        sbf.append(" Extension[").append(extension).append("]");
        sbf.append("{");
        sbf.append("multipartResolver=").append(getClassNameFor(commonsMultipartResolver)).append(",");
        sbf.append("localResolver=").append(getClassNameFor(commonLocaleResolver)).append(",");
        sbf.append("themeResolver=").append(getClassNameFor(commonThemeResolver)).append(",");
        sbf.append("handlerMappings=").append(getClassNameFor(handlerMappings)).append(",");
        sbf.append("handlerAdapters=").append(getClassNameFor(handlerAdapters)).append(",");
        sbf.append("handlerExceptionResolvers=").append(getClassNameFor(handlerExceptionResolvers)).append(",");
        sbf.append("viewNameTranslator=").append(getClassNameFor(viewNameTranslator)).append(",");
        sbf.append("viewResolvers=").append(getClassNameFor(viewResolvers)).append("}");

        return sbf.toString();
    }

    private String getClassNameFor(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }

    private String getClassNameFor(List<?> list) {
        if (list == null || list.isEmpty()) {
            return "size 0";
        }
        StringBuffer sbf = new StringBuffer("{");
        for (Iterator<?> iterator = list.iterator(); iterator.hasNext(); ) {
            Object object = iterator.next();
            sbf.append(getClassNameFor(object));
            sbf.append("|");
        }
        sbf.append("}");

        return sbf.toString();
    }

    public void setMediaTypes(String mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(extension);
        if (StringUtils.isBlank(mediaTypes)) {
            logger.logMessage(LogLevel.WARN, "The property mediaTypes is empty. " + this);
        }
        mediaTypeRegistrarSupport.setMediaTypes(extension, mediaTypes);
        extensionMappingInstanceResolver.addExtensionMapping(this);
    }
}
