package org.tinygroup.aopcache.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cache-actions")
public class CacheActions {

	@XStreamImplicit
	private List<CacheAction> actions;

	public List<CacheAction> getActions() {
		if(actions==null){
			actions=new ArrayList<CacheAction>();
		}
		return actions;
	}

	public void setActions(List<CacheAction> actions) {
		this.actions = actions;
	}
	
}
