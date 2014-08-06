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
package org.tinygroup.logger.impl;

import static org.tinygroup.logger.LogLevel.ERROR;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tinygroup.context.Context;
import org.tinygroup.i18n.I18nMessage;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.logger.MDCAdaptor;

/**
 * @author luoguo
 */
public class LoggerImpl implements Logger {
	private static Pattern pattern = Pattern.compile("[{](.)*?[}]");

	private org.slf4j.Logger logger;
	// MDC适配器
	protected MDCAdaptor MDC = new BasicMDCAdapter();
	private boolean supportTransaction = true;
	private ThreadLocal<LogBuffer> threadLocal = new ThreadLocal<LogBuffer>();
	private static I18nMessage i18nMessage = I18nMessageFactory
			.getI18nMessages();
	/** 日志缓存默认最大记录条数及最大字节数. */
	private static final int DEFAULT_MAX_BUFFER_RECORDS = 80000;

	/** The max buffer records. */
	private int maxBufferRecords = DEFAULT_MAX_BUFFER_RECORDS;

	/** The buffer records. */
	private int bufferRecords = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#isSupportTransaction()
	 */
	public boolean isSupportTransaction() {
		return supportTransaction;
	}

	public void removeLogBuffer() {
		threadLocal.set(null);
	}

	public synchronized LogBuffer getLogBuffer() {
		LogBuffer logBuffer = threadLocal.get();
		if (logBuffer == null) {
			logBuffer = new LogBuffer();
			threadLocal.set(logBuffer);
		}
		return logBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#setSupportTransaction(boolean)
	 */
	public void setSupportTransaction(boolean supportBusiness) {
		this.supportTransaction = supportBusiness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#startTransaction()
	 */
	public void startTransaction() {
		if(supportTransaction){
			LogBuffer logBuffer = getLogBuffer();
			logBuffer.increaseTransactionDepth();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#endTransaction()
	 */
	public void endTransaction() {
		if(supportTransaction){
			LogBuffer logBuffer = getLogBuffer();
			if (logBuffer != null) {
				logBuffer.decreeaseTransactionDepth();
				if (logBuffer.getTimes() == 0) {
					flushLog(logBuffer);
					removeLogBuffer();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#flushTransaction()
	 */
	public void flushTransaction() {
		if(supportTransaction){
			LogBuffer logBuffer = getLogBuffer();
			if (logBuffer != null) {
				flushLog(logBuffer);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#resetTransaction()
	 */
	public void resetTransaction() {
		if(supportTransaction){
			LogBuffer logBuffer = getLogBuffer();
			if (logBuffer != null) {
				logBuffer.reset();
				maxBufferRecords=0;
			}
		}
	}

	private void flushLog(LogBuffer logBuffer) {
		for (Message message : logBuffer.getLogMessages()) {
			if (message.getThrowable() != null) {
				logError(message.getMessage(), message.getThrowable());
			} else {
				pLogMessage(message.getLevel(), message.getMessage());
			}
		}
		logBuffer.getLogMessages().clear();
		bufferRecords = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#getLogger()
	 */
	public org.slf4j.Logger getLogger() {
		return logger;
	}

	public LoggerImpl(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tinygroup.logger.ILogger#isEnabled(org.tinygroup.logger.LogLevel)
	 */
	public boolean isEnabled(LogLevel logLevel) {
		switch (logLevel) {
		case DEBUG:
			return logger.isDebugEnabled();
		case INFO:
			return logger.isInfoEnabled();
		case WARN:
			return logger.isWarnEnabled();
		case TRACE:
			return logger.isTraceEnabled();
		case ERROR:
			return logger.isErrorEnabled();
		}
		return true;
	}

	/**
	 * 直接记录日志
	 * 
	 * @param logLevel
	 * @param message
	 */
	private void pLogMessage(LogLevel logLevel, String message) {
		putThreadVariable();// 在输出日志之前先放入局部线程变量中的mdc值
		switch (logLevel) {
		case DEBUG:
			logger.debug(message);
			break;
		case INFO:
			logger.info(message);
			break;
		case WARN:
			logger.warn(message);
			break;
		case TRACE:
			logger.trace(message);
			break;
		case ERROR:
			logger.error(message);
			break;
		}
	}

	private void putThreadVariable() {
		MDC.clear();
		Map<String, Object> threadVariable = LoggerFactory
				.getThreadVariableMap();
		if (threadVariable != null) {
			for (Entry<String, Object> entry : threadVariable.entrySet()) {
				putToMDC(entry.getKey(), entry.getValue());
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tinygroup.logger.ILogger#logMessage(org.tinygroup.logger.LogLevel,
	 * java.lang.String)
	 */
	public void logMessage(LogLevel logLevel, String message) {
		if (!isEnabled(logLevel)) {
			return;
		}
		LogLevel threadLevel = LoggerFactory.getThreadLogLevel();
		if (threadLevel == null || threadLevel != null
				&& logLevel.toString().equals(threadLevel.toString())) {
			exportLog(logLevel, message);
		}
	}

	private void exportLog(LogLevel logLevel, String message) {
		LogBuffer logBuffer = getLogBuffer();
		if (supportTransaction && logBuffer != null && logBuffer.getTimes() > 0) {
			logBuffer.getLogMessages().add(
					new Message(logLevel, message, System.currentTimeMillis()));
			checkBufferSize(logBuffer);
		} else {
			pLogMessage(logLevel, message);
		}
	}

	/**
	 * 检测日志缓存列表，如果超过了最大限制条数或最大限制记录数，<br>
	 * 则强制执行刷新操作。<br>
	 * .
	 * 
	 * @param logBuffer
	 */
	private void checkBufferSize(LogBuffer logBuffer) {
		bufferRecords++;
		if (bufferRecords >= maxBufferRecords) {
			flushLog(logBuffer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#log(org.tinygroup.logger.LogLevel,
	 * java.lang.String)
	 */
	public void log(LogLevel logLevel, String code) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.getMessage(code));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#log(org.tinygroup.logger.LogLevel,
	 * java.util.Locale, java.lang.String, java.lang.Object)
	 */
	public void log(LogLevel logLevel, Locale locale, String code,
			Object... args) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.getMessage(code, locale, args));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#log(org.tinygroup.logger.LogLevel,
	 * java.lang.String, java.lang.Object)
	 */
	public void log(LogLevel logLevel, String code, Object... args) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.getMessage(code, args));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#log(org.tinygroup.logger.LogLevel,
	 * java.util.Locale, java.lang.String, org.tinygroup.context.Context)
	 */
	public void log(LogLevel logLevel, Locale locale, String code,
			Context context) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.getMessage(code, context, locale));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tinygroup.logger.ILogger#log(org.tinygroup.logger.LogLevel,
	 * java.lang.String, org.tinygroup.context.Context)
	 */
	public void log(LogLevel logLevel, String code, Context context) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.getMessage(code, context));
	}

	public void logMessage(LogLevel logLevel, String message, Object... args) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, format(message, args));

	}

	private String format(String message, Object... args) {
		Matcher matcher = pattern.matcher(message);
		StringBuffer stringBuffer = new StringBuffer();
		int start = 0;
		int count = 0;
		while (matcher.find(start)) {
			stringBuffer.append(message.substring(start, matcher.start()));
			stringBuffer.append(args[count++]);
			start = matcher.end();
			if (count == args.length) {
				break;
			}
		}
		stringBuffer.append(message.substring(start, message.length()));
		return stringBuffer.toString();
	}

	public void logMessage(LogLevel logLevel, String message, Context context) {
		if (!isEnabled(logLevel)) {
			return;
		}
		logMessage(logLevel, i18nMessage.format(message, context));
	}

	public void error(String code) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		log(LogLevel.ERROR, i18nMessage.getMessage(code));
	}

	public void error(String code, Throwable throwable) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logError(i18nMessage.getMessage(code), throwable);
	}

	public void error(String code, Throwable throwable, Object... args) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logError(i18nMessage.getMessage(code, args), throwable);
	}

	public void error(String code, Throwable throwable, Context context) {
		if (!isEnabled(ERROR)) {
			return;
		}
		error(i18nMessage.format(code, context), throwable);
	}

	public void errorMessage(String message) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logMessage(LogLevel.ERROR, message);
	}

	public void errorMessage(String message, Throwable throwable) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logError(message, throwable);
	}

	public void errorMessage(String message, Throwable throwable,
			Object... args) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logError(format(message, args), throwable);
	}

	public void errorMessage(String message, Throwable throwable,
			Context context) {
		if (!isEnabled(LogLevel.ERROR)) {
			return;
		}
		logError(i18nMessage.format(message, context), throwable);
	}

	public void error(Throwable throwable) {
		logError(throwable.getMessage(), throwable);
	}

	private void logError(String message, Throwable throwable) {
		if (!isEnabled(ERROR)) {
			return;
		}
		LogLevel threadLevel = LoggerFactory.getThreadLogLevel();
		if (threadLevel == null || threadLevel != null
				&& LogLevel.ERROR.toString().equals(threadLevel.toString())) {
			LogBuffer logBuffer = getLogBuffer();
			if (supportTransaction && logBuffer != null
					&& logBuffer.getTimes() > 0) {
				logBuffer.getLogMessages().add(
						new Message(ERROR, message, System.currentTimeMillis(),
								throwable));
				checkBufferSize(logBuffer);
			} else {
				putThreadVariable();
				logger.error(message, throwable);
			}
		}

	}

	public void putToMDC(String key, Object value) {
		MDC.put(key, value);
	}

	public void removeFromMDC(String key) {
		MDC.remove(key);
	}

	public int getMaxBufferRecords() {
		return maxBufferRecords;
	}

	public void setMaxBufferRecords(int maxBufferRecords) {
		this.maxBufferRecords = maxBufferRecords;
	}

}
