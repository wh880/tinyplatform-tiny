/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.springmvc.view;

import java.util.Locale;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.template.TemplateEngine;

/**
 * tinytemplate解析器
 * 
 * @author renhui
 * 
 */
public class TinyTemplateLayoutViewResolver extends
		AbstractTemplateViewResolver {

	private static final String VIEW_EXT_FILENAME = "page";// 视图扩展名
	
	private static final String PAGELET_EXT_FILE_NAME = "pagelet";

	private String viewExtFileName = VIEW_EXT_FILENAME;
	
	private String noLayoutExtFileName=PAGELET_EXT_FILE_NAME;
	
	private TemplateEngine templateEngine;
	
	public TinyTemplateLayoutViewResolver() {
		super();
		setViewClass(requiredViewClass());
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void setViewExtFileName(String viewExtFileName) {
		this.viewExtFileName = viewExtFileName;
	}
	

	public String getViewExtFileName() {
		return viewExtFileName;
	}

	public String getNoLayoutExtFileName() {
		return noLayoutExtFileName;
	}

	public void setNoLayoutExtFileName(String noLayoutExtFileName) {
		this.noLayoutExtFileName = noLayoutExtFileName;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		TinyTemplateLayoutView layoutView = (TinyTemplateLayoutView) super
				.buildView(viewName);
		Assert.assertNotNull(templateEngine,"templateEngine must not be null");
		layoutView.setTemplateEngine(templateEngine);
		layoutView.setUrl(getPrefix()+generateUrl(viewName));
		layoutView.setNoLayoutExtFileName(noLayoutExtFileName);
		layoutView.setViewExtFileName(viewExtFileName);

		return layoutView;
	}

	private String generateUrl(String viewName) {
		StringBuffer url=new StringBuffer();
		if(viewName.startsWith("/")){
			url.append(viewName);
		}else{
			url.append("/").append(viewName);
		}
		int sepIndex = viewName.lastIndexOf(".");
		if(sepIndex==-1){
			url.append(".").append(getExtension());
		}
		return url.toString();
	}
	
	private String getExtension(){
		String extension = RequestInstanceHolder.getExtension();
		if(StringUtil.isBlank(extension)||!isExtFit(extension)){
			return viewExtFileName;
		}
		return extension;
	}
	
	private boolean isExtFit(String extension){
		return  viewExtFileName.equals(extension)||noLayoutExtFileName.equals(extension);
	}
	
	
	protected Object getCacheKey(String viewName, Locale locale) {
		String extension = RequestInstanceHolder.getExtension();
		if(StringUtil.isBlank(extension)){
			return super.getCacheKey(viewName, locale);
		}
		return newViewName(viewName,extension)+"_"+locale;
	}
	
	
	private String newViewName(String viewName, String extension) {
		return viewName+"."+extension;
	}

	@Override
	protected Class requiredViewClass() {
		return TinyTemplateLayoutView.class;
	}

}
