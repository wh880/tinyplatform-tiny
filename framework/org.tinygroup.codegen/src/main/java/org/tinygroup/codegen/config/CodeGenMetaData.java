package org.tinygroup.codegen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("code-gen-metadata")
public class CodeGenMetaData {
    /**
     * 分类
     */
    @XStreamAsAttribute
    @XStreamAlias("category")
    private String category;
    /**
     * 图标
     */
    @XStreamAsAttribute
    @XStreamAlias("icon")
    private String icon;
    /**
     * 标题
     */
    @XStreamAsAttribute
    @XStreamAlias("title")
    private String title;
    
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    /**
     * 长描述
     */
    @XStreamAsAttribute
    private String description;
    @XStreamImplicit
    private List<TemplateDefine> templateDefines;
    @XStreamImplicit
    private List<MacroDefine> macroDefines;
    /**
     * 人机界面交互定义方件
     */
    @XStreamAsAttribute
    @XStreamAlias("ui-define-file")
    private String uiDefineFile;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TemplateDefine> getTemplateDefines() {
        if (templateDefines == null) {
            templateDefines = new ArrayList<TemplateDefine>();
        }
        return templateDefines;
    }

    public void setTemplateDefines(List<TemplateDefine> templateDefines) {
        this.templateDefines = templateDefines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUiDefineFile() {
        return uiDefineFile;
    }

    public void setUiDefineFile(String uiDefineFile) {
        this.uiDefineFile = uiDefineFile;
    }

	public List<MacroDefine> getMacroDefines() {
		if(macroDefines==null){
			macroDefines=new ArrayList<MacroDefine>();
		}
		return macroDefines;
	}

	public void setMacroDefines(List<MacroDefine> macroDefines) {
		this.macroDefines = macroDefines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
    
}
