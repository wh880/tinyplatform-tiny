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
package org.tinygroup.templatespringext.springext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateFunction;
import org.tinygroup.templatespringext.FileScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tinytemplate视图解析器
 * @author wll
 * 
 */
public class TinyTemplateLayoutViewResolver extends
		AbstractTemplateViewResolver {

	private static final String VIEW_EXT_FILENAME = "page";// 视图扩展后缀
	private static final String PAGELET_EXT_FILE_NAME = "pagelet";
	private String viewExtFileName = VIEW_EXT_FILENAME;
	private String noLayoutExtFileName=PAGELET_EXT_FILE_NAME;
	private static final String MacroExt = "component";
	private TemplateEngine templateEngine;
	private FileScanner fileScanner;
	private  List<String> classPathList = new ArrayList<String>();
	private boolean checkModify = true;

	public boolean isCheckModify() {
		return checkModify;
	}

	public void setCheckModify(boolean checkModify) {
		this.checkModify = checkModify;
	}

	public FileScanner getFileScanner() {
		return fileScanner;
	}

	public void setFileScanner(FileScanner fileScanner) {
		this.fileScanner = fileScanner;
	}

	public  List<String> getClassPathList() {
		return classPathList;
	}

	public  void setClassPathList(List<String> classPathList) {
		this.classPathList = classPathList;
	}

	public TinyTemplateLayoutViewResolver() {
		super();
		setViewClass(requiredViewClass());
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Override
	protected void initApplicationContext(){
		super.initApplicationContext();
		initEngine();
		initFunctions();
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

	private void initEngine(){
//		fileScanner.setClassPathList(classPathList);
		templateEngine.setCheckModified(checkModify);
		fileScanner.setEngine(templateEngine);
		fileScanner.init();
		fileScanner.scanFile();
		fileScanner.fileProcess();
	}

	private String generateUrl(String viewName) {
		StringBuffer url=new StringBuffer();
		if(viewName.startsWith("/")&&!getPrefix().endsWith("/")){
			url.append(viewName);
		}else if(!viewName.startsWith("/")&&getPrefix().endsWith("/")) {
			url.append(viewName);
		}else if(viewName.startsWith("/")&&getPrefix().endsWith("/")){
			viewName = viewName.substring(viewName.lastIndexOf("/")+1);
			url.append(viewName);
		}else{
			url.append("/").append(viewName);
		}
		int sepIndex = viewName.lastIndexOf(".");
		if(sepIndex==-1){
			url.append(".").append(viewExtFileName);
		}
		return url.toString();
	}

	@Override
	protected Class requiredViewClass() {
		return TinyTemplateLayoutView.class;
	}


	private void initFunctions(){
		Map<String,TemplateFunction> functions = BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), TemplateFunction.class, true, false);
		for(TemplateFunction f : functions.values()){
			templateEngine.addTemplateFunction(f);
		}
	}

}
