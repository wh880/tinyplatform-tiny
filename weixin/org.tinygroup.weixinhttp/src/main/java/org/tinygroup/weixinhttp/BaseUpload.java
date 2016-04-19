package org.tinygroup.weixinhttp;

import org.tinygroup.vfs.FileObject;

public abstract class BaseUpload implements WeiXinHttpUpload{

	private String fileName;
	
	private FileObject fileObject;
	
	private String formName;
	
	private String content;
	
	public BaseUpload(FileObject fileObject){
		this(null,fileObject,null,null);
	}
	
	public BaseUpload(String fileName,FileObject fileObject){
		this(fileName,fileObject,null,null);
	}
	
	public BaseUpload(String fileName,FileObject fileObject,String formName,String content){
		this.fileName =fileName;
		this.fileObject = fileObject;
		this.formName = formName;
		this.content = content;
	}
	
	public FileObject getFileObject() {
		return fileObject;
	}

	public String getFormName() {
		return formName;
	}

	public String getContent() {
		return content;
	}

	public String getFileName() {
		return fileName!=null?fileName:fileObject.getFileName();
	}

}
