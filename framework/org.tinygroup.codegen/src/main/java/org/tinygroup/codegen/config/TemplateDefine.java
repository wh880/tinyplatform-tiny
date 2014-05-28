package org.tinygroup.codegen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 模板定义,一条对应于一个模板文件
 */
@XStreamAlias("template-define")
public class TemplateDefine {
    /**
     * 模板文件路径
     */
    @XStreamAsAttribute
    @XStreamAlias("template-path")
    private String templatePath;
    /**
     * 利用模板文件生成文件时，生成文件的名称模板
     */
    @XStreamAsAttribute
    @XStreamAlias("file-name-template")
    private String fileNameTemplate;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

	public String getFileNameTemplate() {
		return fileNameTemplate;
	}

	public void setFileNameTemplate(String fileNameTemplate) {
		this.fileNameTemplate = fileNameTemplate;
	}
    
    

}
