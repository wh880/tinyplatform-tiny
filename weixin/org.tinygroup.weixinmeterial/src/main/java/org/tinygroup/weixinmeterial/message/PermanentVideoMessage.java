package org.tinygroup.weixinmeterial.message;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传永久视频
 * @author yancheng11334
 *
 */
public class PermanentVideoMessage extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public PermanentVideoMessage(FileObject fileObject,String title,String introduction) {
		this(fileObject,new PermanentVideoForm(title,introduction));
	}
	
	public PermanentVideoMessage(FileObject fileObject,PermanentVideoForm videoForm) {
		super("media",fileObject,"description",videoForm.toString());
	}
	
	public String getWeiXinKey() {
		return "uploadPermanentVideo";
	}

}
