package org.tinygroup.database.config.sequence;

import org.tinygroup.metadata.config.BaseObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("sequence")
public class Sequence extends BaseObject {
	
	@XStreamAsAttribute
	@XStreamAlias("data-type")
	private String dataType;

	@XStreamAlias("increment-by")
	@XStreamAsAttribute
	private int incrementBy;

	@XStreamAlias("start-with")
	@XStreamAsAttribute
	private int startWith;
	@XStreamAlias("value-config")
	private ValueConfig valueConfig;
	@XStreamAsAttribute
	private boolean cycle;
	@XStreamAlias("seq-cache-config")
	private SeqCacheConfig seqCacheConfig;
	@XStreamAsAttribute
	private boolean order;

	public int getIncrementBy() {
		return incrementBy;
	}

	public void setIncrementBy(int incrementBy) {
		this.incrementBy = incrementBy;
	}

	public int getStartWith() {
		return startWith;
	}

	public void setStartWith(int startWith) {
		this.startWith = startWith;
	}

	public ValueConfig getValueConfig() {
		return valueConfig;
	}

	public void setValueConfig(ValueConfig valueConfig) {
		this.valueConfig = valueConfig;
	}

	public boolean isCycle() {
		return cycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	public SeqCacheConfig getSeqCacheConfig() {
		return seqCacheConfig;
	}

	public void setSeqCacheConfig(SeqCacheConfig seqCacheConfig) {
		this.seqCacheConfig = seqCacheConfig;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

}
