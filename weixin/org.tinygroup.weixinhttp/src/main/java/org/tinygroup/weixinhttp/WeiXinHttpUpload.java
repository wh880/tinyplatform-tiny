package org.tinygroup.weixinhttp;

import org.tinygroup.vfs.FileObject;

/**
 * 微信HTTP上传通用构造接口
 * @author yancheng11334
 *
 */
public interface WeiXinHttpUpload {

	/**
	 * 获取微信通讯的URL键值
	 * @return
	 */
	String getWeiXinKey();
	
	/**
	 * 获取构造文件名(不一定与上传文件名一致)
	 * @return
	 */
	String getFileName();
	
	/**
	 * 获得文件实体
	 * @return
	 */
	FileObject getFileObject();
	
	/**
	 * 获得表单名
	 * @return
	 */
	String getFormName();
	
	/**
	 * 获得表单内容
	 * @return
	 */
	String getContent();
}
