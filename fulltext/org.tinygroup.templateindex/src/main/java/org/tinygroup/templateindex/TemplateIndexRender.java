package org.tinygroup.templateindex;

import org.tinygroup.context.Context;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.template.TemplateRender;

/**
 * 基于模板语言的索引渲染器
 * @author yancheng11334
 *
 */
public interface TemplateIndexRender {

	TemplateRender getTemplateRender();
	
	Document execute(Context context);
	
}
