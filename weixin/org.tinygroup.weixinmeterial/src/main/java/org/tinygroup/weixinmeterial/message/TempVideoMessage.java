package org.tinygroup.weixinmeterial.message;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传临时视频
 * @author yancheng11334
 *
 */
public class TempVideoMessage extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public TempVideoMessage(FileObject fileObject) {
		super(fileObject);
	}

	public String getWeiXinKey() {
		return "uploadTempVideo";
	}
}
