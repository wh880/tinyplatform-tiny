package org.tinygroup.exception;

/**
 * 异常号翻译接口的选择器
 * @author renhui
 *
 */
public interface ErrorCodeTranslatorSelector {
	ErrorCodeTranslator select(ErrorContext errorContext);
}
