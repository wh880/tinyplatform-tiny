package org.tinygroup.fileindexsource.impl;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectFilter;

/**
 * 默认的过滤规则
 * @author yancheng11334
 *
 */
public class DefaultFileObjectFilter implements FileObjectFilter{

	public boolean accept(FileObject fileObject) {
		return true;
	}
	
}
