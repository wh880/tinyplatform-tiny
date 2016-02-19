/**
 * 
 */
package org.tinygroup.remoteconfig.web.action.pojo;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 * 
 */
public class ConfigViewItem {

	String id;
	/**
	 * 老子的ID
	 */
	String parentId;
	String key;
	String value;
	String desc;
	String createTime;
	String modifyTime;
	String app;
	String env;
	String version;
	//是否被重写
	boolean rewrite = false;

	public ConfigViewItem() {
	}

	public ConfigViewItem(String key, String value) {
		this.key = key;
		this.value = value;
		parseNode();
	}

	// public ConfigViewItem(String id, String parentId, String key, String
	// value,
	// String desc, String createTime, String modifyTime) {
	// this.id = id;
	// this.parentId = parentId;
	// this.key = key;
	// this.value = value;
	// this.desc = desc;
	// this.createTime = createTime;
	// this.modifyTime = modifyTime;
	// if (StringUtils.isNotBlank(id)) {
	// parseNode();
	// }
	// }

	private void parseNode() {
		String[] nodes = StringUtils.split(key, "/");
		if (nodes.length > 0) {
			app = nodes[0];
		}
		if (nodes.length > 1) {
			env = nodes[1];
		}
		if (nodes.length > 2) {
			version = nodes[2];
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isRewrite() {
		return rewrite;
	}

	public void setRewrite(boolean rewrite) {
		this.rewrite = rewrite;
	}

}
