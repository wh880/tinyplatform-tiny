package org.tinygroup.fulltext;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.document.HighlightDocument;
import org.tinygroup.fulltext.exception.FullTextException;

/**
 * 全文检索全局辅助类
 * @author yancheng11334
 *
 */
public class FullTextHelper {

	private static volatile  String _id="_id";
	private static volatile  String _type="_type";
	private static volatile  String _title="_title";
	private static volatile  String _abstract="_abstract";
	
	/**
	 * 得到默认的ID存储名
	 * @return
	 */
	public static String getStoreId(){
		return _id;
	}
	
	/**
	 * 得到默认的type存储名
	 * @return
	 */
	public static String getStoreType(){
		return _type;
	}
	
	/**
	 * 得到默认的title存储名
	 * @return
	 */
	public static String getStoreTitle(){
		return _title;
	}
	
	/**
	 * 得到默认的abstract存储名
	 * @return
	 */
	public static String getStoreAbstract(){
		return _abstract;
	}
	
	/**
	 * 设置默认的ID存储名
	 * @param id
	 */
	public static void setStoreId(String id){
		_id = id;
	}
	
	/**
	 * 设置默认的type存储名
	 * @param type
	 */
	public static void setStoreType(String type){
		_type = type;
	}
	
	/**
	 * 设置默认的title存储名
	 * @param type
	 */
	public static void setStoreTitle(String title){
		_title = title;
	}
	
	/**
	 * 设置默认的type存储名
	 * @param type
	 */
	public static void setStoreAbstract(String Abstract){
		_abstract = Abstract;
	}
	
	/**
	 * 得到FullText的实例
	 * @return
	 */
	public static FullText getFullText(){
		String bean = ConfigurationUtil.getConfigurationManager()
				.getConfiguration(FullText.FULLTEXT_BEAN_NAME);
		if(StringUtil.isEmpty(bean)){
		   throw new FullTextException(String.format("FullText的bean配置不存在，请检查%s全局配置参数!",FullText.FULLTEXT_BEAN_NAME));
		}
		return (FullText) BeanContainerFactory.getBeanContainer(FullTextHelper.class.getClassLoader()).getBean(bean);
	}
	
	/**
	 * 高亮结果
	 * @param pager
	 * @param arguments
	 * @return
	 */
	public static Pager<HighlightDocument> highlight(Pager<Document> pager,Object... arguments){
		if(pager==null){
		   return null;
		}
		List<HighlightDocument> list = new ArrayList<HighlightDocument>();
		if(pager.getRecords()!=null){
		   for(Document doc:pager.getRecords()){
			   list.add(new HighlightDocument(doc,arguments));
		   }
		}
		return new Pager<HighlightDocument>(pager.getTotalCount(),pager.getStart(),pager.getLimit(),list);
	}
	
}
