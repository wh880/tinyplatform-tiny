package org.tinygroup.springmvc.multipart;

import org.springframework.web.multipart.MultipartFile;
import org.tinygroup.vfs.FileObject;

/**
 * MultipartFile的扩展接口
 * @author renhui
 *
 */
public interface TinyMultipartFile extends MultipartFile {

	/**
	 * 转换成FileObject对象
	 * @return
	 */
	FileObject toFileObject(); 
	

}
