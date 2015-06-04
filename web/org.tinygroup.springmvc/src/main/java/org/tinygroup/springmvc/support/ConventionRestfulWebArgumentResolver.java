package org.tinygroup.springmvc.support;

import java.beans.PropertyEditor;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.tinygroup.springmvc.coc.impl.RestfulConventionHandlerMethodResolver;

/**
 * 针对性解决约定Restful开发时，参数绑定问题；
 * 
 */
public class ConventionRestfulWebArgumentResolver extends
		PropertyEditorRegistrySupport implements WebArgumentResolver {

	public ConventionRestfulWebArgumentResolver() {
		registerDefaultEditors();
	}

	@SuppressWarnings("unchecked")
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {

		if (!isConventionalRestful(webRequest)) {
			return UNRESOLVED;
		}
		// and the paramName is id
		if (!StringUtils.equals("id", methodParameter.getParameterName())) {
			return UNRESOLVED;
		}

		Map<String, String> uriTemplateVariables = null;
		if (BeanUtils.isSimpleProperty(methodParameter.getParameterType())) {
			uriTemplateVariables = (Map<String, String>) webRequest
					.getAttribute(
							HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
							RequestAttributes.SCOPE_REQUEST);
		}
		if (uriTemplateVariables != null) {
			String val = uriTemplateVariables.get(methodParameter
					.getParameterName());
			if (val != null
					&& String.class.equals(methodParameter.getParameterType())) {
				return val;
			} else if (val != null) {
				PropertyEditor editor = getDefaultEditor(methodParameter
						.getParameterType());
				if (editor != null) {
					editor.setAsText(val);
					return editor.getValue();
				}
			}
		}
		return UNRESOLVED;
	}

	private boolean isConventionalRestful(NativeWebRequest webRequest) {
		Object obj = webRequest
				.getAttribute(
						RestfulConventionHandlerMethodResolver.RESTFUL_CONVENTION_VIEW_PATH,
						RequestAttributes.SCOPE_REQUEST);
		return obj == null ? false : true;
	}
}
