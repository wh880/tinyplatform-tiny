package org.tinygroup.codegen.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("code-metadata")
public class CodeGenMetaData {
    @XStreamAsAttribute
    @XStreamAlias("catalog")
	private String catalog;
    @XStreamAsAttribute
    @XStreamAlias("icon")
	private String icon;
    @XStreamAsAttribute
    @XStreamAlias("title")
	private String title;
	@XStreamImplicit
	private List<Template> templates;
	@XStreamAsAttribute
    @XStreamAlias("ui-path")
	private String uiPath;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
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

	public List<Template> getTemplates() {
		if(templates==null){
			templates=new ArrayList<Template>();
		}
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public String getUiPath() {
		return uiPath;
	}

	public void setUiPath(String uiPath) {
		this.uiPath = uiPath;
	}
	
}
