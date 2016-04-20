package org.tinygroup.weixinmeterial.message;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传临时语音
 * @author yancheng11334
 *
 */
public class TempVoiceMessage extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public TempVoiceMessage(FileObject fileObject) {
		super(fileObject);
	}
	
	public String getWeiXinKey() {
		return "uploadTempVoice";
	}

}
