package org.tinygroup.springmvc.view;

import java.util.List;

import org.springframework.web.servlet.View;

public class DefaultViewsStorage {
	
	private List<View> defaultViews;

	public List<View> getDefaultViews() {
		return defaultViews;
	}

	public void setDefaultViews(List<View> defaultViews) {
		this.defaultViews = defaultViews;
	}
	
}
