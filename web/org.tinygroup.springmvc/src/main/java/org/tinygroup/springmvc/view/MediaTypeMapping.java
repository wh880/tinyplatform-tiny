package org.tinygroup.springmvc.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储MediaType映射关系的对象
 * @author renhui
 *
 */
public class MediaTypeMapping {

	private Map<String, String> mediaTypes=new ConcurrentHashMap<String, String>();

	public Map<String, String> getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(Map<String, String> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

}
