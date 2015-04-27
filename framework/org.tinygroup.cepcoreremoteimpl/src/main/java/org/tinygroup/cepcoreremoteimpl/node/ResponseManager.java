package org.tinygroup.cepcoreremoteimpl.node;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.tinygroup.event.Event;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.nettyremote.Exception.TinyRemoteConnectException;

public class ResponseManager {
	private static Logger logger = LoggerFactory
			.getLogger(ResponseManager.class);
	private static final ConcurrentHashMap<String, BlockingQueue<Event>> responseMap = new ConcurrentHashMap<String, BlockingQueue<Event>>();

	public static void updateResponse(String eventId, Event event) {
		BlockingQueue<Event> queue = responseMap.get(event.getEventId());
		queue.add(event);
	}

	public static void putIfAbsent(String eventId) {
		responseMap.putIfAbsent(eventId, new LinkedBlockingQueue<Event>(1));
	}

	public static Event getResponse(String eventId) {
		Event result;
		logger.logMessage(LogLevel.INFO, "发送请求{0}", eventId);
		try {
			result = responseMap.get(eventId).take(); // TODO:此处改为poll(timeout,unit)

		} catch (final InterruptedException ex) {
			throw new TinyRemoteConnectException("请求" + eventId + "超时", ex);
		} finally {
			responseMap.remove(eventId);
		}
		Throwable throwable = result.getThrowable();
		if (throwable != null) {// 如果有异常发生，则抛出异常
			if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else if (throwable instanceof Error) {
				throw (Error) throwable;
			} else {
				throw new RuntimeException(throwable);// TODO:此处的RuntimeException类型需要调整
			}
		}
		return result;
	}
}
