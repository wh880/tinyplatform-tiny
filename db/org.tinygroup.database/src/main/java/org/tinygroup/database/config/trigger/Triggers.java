package org.tinygroup.database.config.trigger;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("triggers")
public class Triggers {

	@XStreamImplicit
	private List<Trigger> triggers;

	public List<Trigger> getTriggers() {
		if(triggers==null){
			triggers=new ArrayList<Trigger>();
		}
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}
	
}
