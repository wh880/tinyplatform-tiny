
package org.tinygroup.tinysqldsl.base;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author renhui
 *
 */
public interface DslKeyHolder {
	
	Number getKey();

	Map getKeys();
	
	List getKeyList();

}
