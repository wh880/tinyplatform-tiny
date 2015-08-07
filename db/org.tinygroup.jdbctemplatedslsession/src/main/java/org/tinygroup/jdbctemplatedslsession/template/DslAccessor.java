package org.tinygroup.jdbctemplatedslsession.template;

import org.springframework.beans.factory.InitializingBean;
import org.tinygroup.tinysqldsl.DslSession;

public abstract class DslAccessor implements InitializingBean {
	
	protected DslSession dslSession;

	public void afterPropertiesSet(){
		if (getDslSession() == null) {
			throw new IllegalArgumentException("Property 'dslSession' is required");
		}
	}
	
	public DslSession getDslSession() {
		return dslSession;
	}

	public void setDslSession(DslSession dslSession) {
		this.dslSession=dslSession;
	}


}
