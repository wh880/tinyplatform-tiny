/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter.pojo;

import java.io.Serializable;

import org.tinygroup.exception.BaseRuntimeException;

/**
 * @author yanwj
 * 
 */
public class RemoteConfigStat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1094780565982608091L;
	String node;
	OperationStatEnum stat;
	BaseRuntimeException exception;

	public RemoteConfigStat(String node, OperationStatEnum stat,
			BaseRuntimeException exception) {
		this.node = node;
		this.stat = stat;
		this.exception = exception;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public OperationStatEnum getStat() {
		return stat;
	}

	public void setStat(OperationStatEnum stat) {
		this.stat = stat;
	}

	public BaseRuntimeException getException() {
		return exception;
	}

	public void setException(BaseRuntimeException exception) {
		this.exception = exception;
	}

}
