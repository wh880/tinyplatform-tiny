package org.tinygroup.template.interpret;

import org.antlr.v4.runtime.ParserRuleContext;
import org.tinygroup.template.Macro;
import org.tinygroup.template.TemplateException;

/**
 * 宏异常，解决宏和页面报错的提示路径问题
 * 
 * @author yancheng11334
 * 
 */
public class MacroException extends TemplateException {

	private Macro macro;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726495316708790421L;


	public MacroException(Macro macro, Exception e) {
		super(e);
		this.macro = macro;
	}
	
	 public String getMessage() {
		 StringBuffer sb = new StringBuffer();
		 sb.append("宏文件路径:").append(macro.getMacroPath()).append(",宏名称:").append(macro.getName());
		 if(getContext()!=null){
			 ParserRuleContext context = getContext();
			 sb.append("\n===================================================================\n");
			 sb.append(context.getText());
			 sb.append("\n===================================================================\n");
		 }
		 return sb.toString();
	 }
	 
	 private boolean checkCause(){
		 return !(getCause() != null && getCause() instanceof MacroException);
	 }
	/**
	 * 得到宏
	 * @return
	 */
	public Macro getMacro(){
		return macro;
	}
	
}
