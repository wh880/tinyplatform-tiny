package org.tinygroup.database.config.sequence;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("value-config")
public class ValueConfig {

	@XStreamAlias("min-value")
	@XStreamAsAttribute
	private Integer minValue;

	@XStreamAsAttribute
	@XStreamAlias("max-value")
	private Integer maxValue;

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

}
