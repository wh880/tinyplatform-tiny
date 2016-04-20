package org.tinygroup.weixinuser.result;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获得用户基本信息
 * @author yancheng11334
 *
 */
public class UserInfoResult implements ToServerResult{

	int subscribe;
	
	@JSONField(name="openid")
	String openId;
	
	@JSONField(name="nickname")
	String nickName;
	
	int sex;
	
	String language;
	
	String city;
	
	String province;
	
	String country;
	
	@JSONField(name="headimgurl")
	String headImgUrl;
	
	@JSONField(name="subscribe_time")
	String subscribeTime;
	
	@JSONField(name="unionid")
	String unionId;
	
	String remark;
	
	@JSONField(name="gourpid")
	int gourpId;

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getGourpId() {
		return gourpId;
	}

	public void setGourpId(int gourpId) {
		this.gourpId = gourpId;
	}
	
	public String toString(){
		ObjectToJson<UserInfoResult> json= new ObjectToJson<UserInfoResult>();
		return json.convert(this);
	}
}
