package org.tinygroup.exception.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.exception.CommonError;
import org.tinygroup.exception.ErrorCodeTranslator;
import org.tinygroup.exception.ErrorCodeTranslatorSelector;
import org.tinygroup.exception.ErrorContext;

/**
 * 存在多个异常号翻译对象,根据commonerror的errorcode进行选择
 * @author renhui
 *
 */
public class MultiErrorCodeSelector implements ErrorCodeTranslatorSelector {

	private Map<String, ErrorCodeTranslator> translators = new HashMap<String, ErrorCodeTranslator>();

	public ErrorCodeTranslator select(ErrorContext errorContext) {
		List<CommonError> commonErrors = errorContext.getErrorStack();
		if (!CollectionUtil.isEmpty(commonErrors)) {
			for (CommonError commonError : commonErrors) {
				if (translators.containsKey(commonError.getErrorCode()
						.toString())) {
					return translators.get(commonError.getErrorCode()
							.toString());
				}
			}
		}
		return null;
	}

	public Map<String, ErrorCodeTranslator> getTranslators() {
		return translators;
	}

	public void setTranslators(Map<String, ErrorCodeTranslator> translators) {
		this.translators = translators;
	}

	public void addTranslator(ErrorCodeTranslator translator) {
		translators.put(translator.getErrorCode(), translator);
	}

	public void removeTranslator(ErrorCodeTranslator translator) {
		translators.remove(translator.getErrorCode());
	}

}
