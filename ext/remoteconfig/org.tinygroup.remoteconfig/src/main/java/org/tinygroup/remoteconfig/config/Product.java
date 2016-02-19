package org.tinygroup.remoteconfig.config;

import java.util.List;

public class Product {
	/**
	 * 英文名
	 */
	String name;
	/**
	 * 标题
	 */
	String title;
	
	String description;
	
	List<Version> versions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

}
