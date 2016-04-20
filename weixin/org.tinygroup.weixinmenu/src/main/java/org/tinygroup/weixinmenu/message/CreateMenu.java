package org.tinygroup.weixinmenu.message;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinmenu.button.CommonButton;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建微信自定义菜单
 * @author yancheng11334
 *
 */
public class CreateMenu implements ToServerMessage{

	@JSONField(name="button")
	private List<CommonButton> buttons;

	public List<CommonButton> getButtons() {
		if(buttons==null){
		   buttons = new ArrayList<CommonButton>();
		}
		return buttons;
	}

	public void setButtons(List<CommonButton> buttons) {
		this.buttons = buttons;
	}
	
	public void addButton(CommonButton button){
		getButtons().add(button);
	}
	
	public void removeButton(CommonButton button){
		getButtons().remove(button);
	}
	
	public String toString(){
		ObjectToJson<CreateMenu> json= new ObjectToJson<CreateMenu>();
		return json.convert(this);
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "createMenu";
	}
}
