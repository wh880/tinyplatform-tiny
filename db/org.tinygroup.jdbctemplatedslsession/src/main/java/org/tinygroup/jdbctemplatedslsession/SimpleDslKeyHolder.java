package org.tinygroup.jdbctemplatedslsession;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.tinygroup.tinysqldsl.base.DslKeyHolder;

/**
 * spring版本的keyholder
 * @author renhui
 *
 */
public class SimpleDslKeyHolder implements DslKeyHolder {
	
	private KeyHolder keyHolder;
	
	public SimpleDslKeyHolder(KeyHolder keyHolder) {
		super();
		this.keyHolder = keyHolder;
	}

	public Number getKey() {
		return keyHolder.getKey();
	}

	public Map getKeys() {
		return keyHolder.getKeys();
	}

	public List getKeyList() {
		return keyHolder.getKeyList();
	}

}
