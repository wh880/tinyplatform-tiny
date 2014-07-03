package org.tinygroup.database.config.trigger;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.database.config.SqlBody;
import org.tinygroup.metadata.config.BaseObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author renhui
 *
 */
@XStreamAlias("trigger")
public class Trigger extends BaseObject{

	@XStreamAlias("sqls")
	private List<SqlBody> triggerSqls;

	public List<SqlBody> getTriggerSqls() {
		if(triggerSqls==null){
			triggerSqls=new ArrayList<SqlBody>();
		}
		return triggerSqls;
	}

	public void setTriggerSqls(List<SqlBody> triggerSqls) {
		this.triggerSqls = triggerSqls;
	}

}
