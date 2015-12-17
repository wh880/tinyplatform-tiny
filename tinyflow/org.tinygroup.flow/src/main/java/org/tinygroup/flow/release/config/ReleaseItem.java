/**
 * 
 */
package org.tinygroup.flow.release.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author Administrator
 * 
 */
@XStreamAlias("items")
public class ReleaseItem {

	@XStreamImplicit(itemFieldName = "id")
	List<String> items = new ArrayList<String>();

	public List<String> getItems() {
		if (items == null) {
			items = new ArrayList<String>();
		}
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

}
