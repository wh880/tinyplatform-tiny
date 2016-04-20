package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;

/**
 * 上传视频表单
 * @author yancheng11334
 *
 */
public class PermanentVideoForm {

	private String title;
	
	private String introduction;
	
	public PermanentVideoForm(){
		
	}
	
    public PermanentVideoForm(String title,String introduction){
		this.title = title;
		this.introduction = introduction;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String toString(){
		ObjectToJson<PermanentVideoForm> json= new ObjectToJson<PermanentVideoForm>();
		return json.convert(this);
	}
}
