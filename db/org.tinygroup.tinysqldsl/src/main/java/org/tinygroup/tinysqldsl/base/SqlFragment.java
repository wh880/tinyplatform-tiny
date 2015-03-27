package org.tinygroup.tinysqldsl.base;

import org.tinygroup.tinysqldsl.expression.ExpressionFragment;
import org.tinygroup.tinysqldsl.formitem.FromItemFragment;
import org.tinygroup.tinysqldsl.selectitem.SelectItemFragment;

public class SqlFragment {
	
	private String fragment;

	public SqlFragment(String fragment) {
		super();
		this.fragment = fragment;
	}
	
	public String getFragment() {
		return fragment;
	}
	
	public static SelectItemFragment selectFragment(String fragment){
		return new SelectItemFragment(fragment);
	}
	
	public static FromItemFragment fromFragment(String fragment){
		return new FromItemFragment(fragment);
	}
	
	public static Condition conditionFragment(String fragment,Object... values){
		  ExpressionFragment expressionFragment= new ExpressionFragment(fragment);
		  return new Condition(expressionFragment, values);
	}

	@Override
	public String toString() {
		return fragment;
	}
}
