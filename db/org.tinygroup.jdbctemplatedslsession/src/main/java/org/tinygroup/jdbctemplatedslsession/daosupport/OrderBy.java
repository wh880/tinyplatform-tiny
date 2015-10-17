package org.tinygroup.jdbctemplatedslsession.daosupport;


import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.select.OrderByElement;

/**
 * 排序信息
 * @author renhui
 *
 */
public class OrderBy {

	private OrderByElement orderByElement;
	
	public OrderBy(Expression expression, boolean asc) {
		super();
		if(asc){
			orderByElement=OrderByElement.asc(expression);
		}else{
			orderByElement=OrderByElement.desc(expression);
		}
	}
	
	public OrderBy(String columnName,boolean asc){
		super();
		if(asc){
			orderByElement=OrderByElement.asc(new Column(columnName));
		}else{
			orderByElement=OrderByElement.desc(new Column(columnName));
		}
	}
	
	public OrderBy(Column column,boolean asc){
		super();
		if(asc){
			orderByElement=OrderByElement.asc(column);
		}else{
			orderByElement=OrderByElement.desc(column);
		}
	}

	public OrderByElement getOrderByElement() {
		return orderByElement;
	}
	
}
