/**
 * 
 */
package org.tinygroup.remoteconfig.service;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;

/**
 * 默认环境
 * 
 * @author yanwj
 * 
 */
public enum Environment {

	//初始环境，提供配置项的公共库
	DEFAULT( IRemoteConfigConstant.DEFAULT_ENV, "default(初始环境)"),
	//其他模块允许修改公共环境下的配置，但是不允许新增和删除
	RD( "rd", "rd(开发环境)"), 
	QA( "qa", "qa(测试环境)"), 
	LOCAL( "local", "local(本地环境)"), 
	ONLINE( "online", "online(生产环境)");

	String name;
	String desc;

	Environment( String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Environment[] getAllEnv() {
		return values();
	}

	/**
	 * 查询类型是否被允许
	 * 
	 * @param type
	 * @return
	 */
	public Environment isAcceptType(String type){
		for (Environment env : values()) {
			if (StringUtils.equals(env.getName(), type)) {
				return env;
			}
		}
		return null;
	}
	
}
