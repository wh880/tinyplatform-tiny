package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.BundleDefineCompare;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.exception.NotComparableException;

/**
 * 比较两个服务的大小
 *
 * @author luoguo
 *
 */
public class BundleDefineCompareImpl implements BundleDefineCompare{
	private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
	private Logger logger = LoggerFactory.getLogger(NotComparableException.class);
	public int compare(BundleDefine source, BundleDefine dest) {
		boolean isSameServiceId = isSame(source.getId(), dest.getId());
		if (isSameServiceId) {
			return source.getVersion().compareTo(dest.getVersion());
		}
		logger.log(LogLevel.ERROR,"plugin.compareExeception",
				source.getId(),source.getVersion(),dest.getId(),dest.getVersion());
		throw new NotComparableException(
				i18nMessages.getMessage("plugin.compareExeception", 
				source.getId(),source.getVersion(),dest.getId(),dest.getVersion())
		);
	}

	private boolean isSame(String source, String dest) {
		return source.equals(dest);
	}
}
