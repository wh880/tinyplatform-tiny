package org.tinygroup.weixinmeterial.message;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传永久图片
 * @author yancheng11334
 *
 */
public class PermanentImageMessage extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public PermanentImageMessage(FileObject fileObject) {
		super("media",fileObject);
	}

	public String getWeiXinKey() {
		return "uploadPermanentImage";
	}
}
