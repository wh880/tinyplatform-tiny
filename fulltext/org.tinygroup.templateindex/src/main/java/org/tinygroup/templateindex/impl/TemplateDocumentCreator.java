package org.tinygroup.templateindex.impl;

import org.tinygroup.context.Context;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.DefaultDocument;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.Field;
import org.tinygroup.fulltext.field.StringField;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;

/**
 * 基于模板引擎的文档创建，简化操作规则
 * @author yancheng11334
 *
 */
@SuppressWarnings("rawtypes")
public class TemplateDocumentCreator extends AbstractTemplateIndexRender{
    

	private String idRule;
	private String typeRule;
	private String titleRule;
	private String abstractRule;
	
	public TemplateDocumentCreator(){
		super();
	}
	
	public TemplateDocumentCreator(String idRule, String typeRule,
			String titleRule, String abstractRule) {
		super();
		this.idRule = idRule;
		this.typeRule = typeRule;
		this.titleRule = titleRule;
		this.abstractRule = abstractRule;
	}
	
	public String getIdRule() {
		return idRule;
	}
	public void setIdRule(String idRule) {
		this.idRule = idRule;
	}
	public String getTypeRule() {
		return typeRule;
	}
	public void setTypeRule(String typeRule) {
		this.typeRule = typeRule;
	}
	public String getTitleRule() {
		return titleRule;
	}
	public void setTitleRule(String titleRule) {
		this.titleRule = titleRule;
	}
	public String getAbstractRule() {
		return abstractRule;
	}
	public void setAbstractRule(String abstractRule) {
		this.abstractRule = abstractRule;
	}

	/**
	 * 执行渲染逻辑
	 * @param context
	 * @return
	 */
	public Document execute(Context context){
		TemplateContext templateContext = new TemplateContextDefault();
		templateContext.setParent(context);
		DefaultDocument document = new DefaultDocument();
		try{
			document.addField(renderId(templateContext));
			document.addField(renderType(templateContext));
			document.addField(renderTitle(templateContext));
			document.addField(renderAbstarctRule(templateContext));
		}catch(Exception e){
			throw new FullTextException(e);
		}finally{
			templateContext.setParent(null);
		}
		return document;
	}
	
	protected Field renderId(TemplateContext templateContext) throws TemplateException{
		String value = getTemplateRender().renderTemplateWithOutLayout(idRule, templateContext);
		return new StringField(FullTextHelper.getStoreId(),value) ;
	}
	
	protected Field renderType(TemplateContext templateContext) throws TemplateException{
		String value = getTemplateRender().renderTemplateWithOutLayout(typeRule, templateContext);
		return new StringField(FullTextHelper.getStoreType(),value) ;
	}
	
	protected Field renderTitle(TemplateContext templateContext) throws TemplateException{
		String value = getTemplateRender().renderTemplateWithOutLayout(titleRule, templateContext);
		return new StringField(FullTextHelper.getStoreTitle(),value,true,true,true) ;
	}
	
	protected Field renderAbstarctRule(TemplateContext templateContext) throws TemplateException{
		String value = getTemplateRender().renderTemplateWithOutLayout(abstractRule, templateContext);
		return new StringField(FullTextHelper.getStoreAbstract(),value,true,true,true) ;
	}
}
