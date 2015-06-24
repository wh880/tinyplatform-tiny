package org.tinygroup.metadata.config.stddatatype;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 数据库语言列表
 * @author yancheng11334
 *
 */
@XStreamAlias("language-types")
public class LanguageTypes {

	@XStreamImplicit
	private List<LanguageType> languageTypeList;

	public List<LanguageType> getLanguageTypeList() {
		return languageTypeList;
	}

	public void setLanguageTypeList(List<LanguageType> languageTypeList) {
		this.languageTypeList = languageTypeList;
	}
	
}
