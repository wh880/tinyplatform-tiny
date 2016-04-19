package org.tinygroup.weixinmeterial.message;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传临时缩略图
 * @author yancheng11334
 *
 */
public class TempThumbMessage extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public TempThumbMessage(FileObject fileObject) {
		super(fileObject);
	}

	public String getWeiXinKey() {
		return "uploadTempThumb";
	}
}
