/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter.pojo;

/**
 * @author yanwj
 *
 */
public enum OperationStatEnum {

	SUCCESS("success" ,"操作成功"),
	FAILED("failed" ,"操作失败");
	
	String code;
	String desc;
	
	private OperationStatEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
