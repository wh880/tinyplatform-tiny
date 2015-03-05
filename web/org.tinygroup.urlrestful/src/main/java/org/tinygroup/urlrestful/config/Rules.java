package org.tinygroup.urlrestful.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("rules")
public class Rules {
	
	@XStreamImplicit
	private List<Rule> rules;

	public List<Rule> getRules() {
		if(rules ==null){
			rules =new ArrayList<Rule>();
		}
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

}
