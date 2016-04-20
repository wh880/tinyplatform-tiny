package org.tinygroup.weixinuser.result;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取用户列表(只包含openid)
 * @author yancheng11334
 *
 */
public class UserListResult implements ToServerResult{

	private int total;
	
	private int count;
	
	@JSONField(name="data")
	private UserListItem userListItem;
	
	@JSONField(name="next_openid")
	private String nextOpenId;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public UserListItem getUserListItem() {
		return userListItem;
	}

	public void setUserListItem(UserListItem userListItem) {
		this.userListItem = userListItem;
	}

	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
	
	public String toString(){
		ObjectToJson<UserListResult> json= new ObjectToJson<UserListResult>();
		return json.convert(this);
	}
	
}
