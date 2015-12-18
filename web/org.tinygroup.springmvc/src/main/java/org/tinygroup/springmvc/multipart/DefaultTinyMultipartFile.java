package org.tinygroup.springmvc.multipart;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.weblayer.webcontext.parser.fileupload.TinyFileItem;
import org.tinygroup.weblayer.webcontext.parser.fileupload.TinyItemFileObject;
import org.tinygroup.weblayer.webcontext.parser.impl.DiskFileItem;
import org.tinygroup.weblayer.webcontext.parser.impl.FileObjectInDisk;
import org.tinygroup.weblayer.webcontext.parser.impl.FileObjectInMemory;
import org.tinygroup.weblayer.webcontext.parser.impl.InMemoryFormFieldItem;

/**
 * TinyMultipartFile接口默认实现
 * @author renhui
 *
 */
public class DefaultTinyMultipartFile extends CommonsMultipartFile implements TinyMultipartFile {
	
	
	public DefaultTinyMultipartFile(FileItem fileItem) {
		super(fileItem);
	}

	public FileObject toFileObject() {
		FileObject fileObject = null;
		FileItem item=getFileItem();
		if (item instanceof InMemoryFormFieldItem) {
			fileObject = new FileObjectInMemory(
					(InMemoryFormFieldItem) item);
		} else if (item instanceof DiskFileItem) {
			fileObject = new FileObjectInDisk((DiskFileItem) item);
		} else if (item instanceof TinyFileItem) {
			fileObject = new TinyItemFileObject((TinyFileItem) item);
		}
		return fileObject;
	}

}
