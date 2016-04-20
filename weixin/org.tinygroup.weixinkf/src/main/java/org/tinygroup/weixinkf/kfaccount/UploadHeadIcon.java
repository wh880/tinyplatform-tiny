package org.tinygroup.weixinkf.kfaccount;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinhttp.BaseUpload;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 上传客服头像接口对象
 * @author yancheng11334
 *
 */
public class UploadHeadIcon extends BaseUpload implements WeiXinHttpUpload,ToServerMessage{

	public UploadHeadIcon(FileObject fileObject,String accountId) {
		super("media",fileObject);
		this.accountId = accountId;
	}
	
	String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getWeiXinKey() {
		return "uploadHeadIcon";
	}

}
