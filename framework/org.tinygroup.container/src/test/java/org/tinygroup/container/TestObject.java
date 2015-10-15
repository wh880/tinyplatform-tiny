/**
 * 
 */
package org.tinygroup.container;

/**
 * @author Administrator
 *
 */
public class TestObject implements BaseObject<String> {

	String id;
	int order;
	String name;
	String title;
	String description;

	public TestObject(String id, int order, String name, String title ,String description) {
		super();
		this.id = id;
		this.order = order;
		this.name = name;
		this.title = title;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
