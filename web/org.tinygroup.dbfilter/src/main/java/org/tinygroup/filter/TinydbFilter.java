package org.tinygroup.filter;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.util.TinyDBUtil;
import org.tinygroup.weblayer.AbstractTinyFilter;
import org.tinygroup.weblayer.WebContext;

/**
 * 
 * 功能说明:获取beantype参数,组装成Bean对象,存放到context 
 * 开发人员: renhui <br>
 * 开发时间: 2014-2-20 <br>
 */
public class TinydbFilter extends AbstractTinyFilter{
	
	private static final String SPLIT = ",";
	private static final String BEAN_TYPE_KEY="@beantype";
	private Logger logger=LoggerFactory.getLogger(TinydbFilter.class);


	public void preProcess(WebContext context) {
		String beanType=context.get(BEAN_TYPE_KEY);
		if(!StringUtil.isBlank(beanType)){
			String[] types=beanType.split(SPLIT);
			for (String type : types) {
				if(context.get(type)!=null){
					logger.logMessage(LogLevel.WARN, "已经存在名称：{0}的参数",type);
				}else{
					Bean bean=TinyDBUtil.context2Bean(context, type);
					context.put(type, bean);
				}
			}
		}
	}
	
}
