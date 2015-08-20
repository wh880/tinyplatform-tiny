package org.tinygroup.template.function;

import java.util.Random;
import java.util.UUID;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

/**
 * 生成随机值的函数
 * @author yancheng11334
 *
 */
public class RandomFunction extends AbstractTemplateFunction {

	public RandomFunction() {
		super("rand,random");
	}

	public Object execute(Template template, TemplateContext context,
			Object... parameters) throws TemplateException {
		if(parameters==null || parameters.length<1){
		   return nextInt(template,context);
		}else{
		   String type = (String) parameters[0];
		   if(type==null || type.equalsIgnoreCase("int")){
			  return nextInt(template,context); 
		   }else if(type.equalsIgnoreCase("long")){
			   return nextLong(template,context); 
		   }else if(type.equalsIgnoreCase("uuid")){
			   return nextUUID(template,context); 
		   }else if(type.equalsIgnoreCase("double")){
			   return nextDouble(template,context); 
		   }else if(type.equalsIgnoreCase("float")){
			   return nextFloat(template,context); 
		   }
		   //没有任何匹配返回Null值
		   return null;
		}
	}
	
	/**
	 * 生成int型随机数
	 * @param template
	 * @param context
	 * @return
	 */
	protected Integer nextInt(Template template, TemplateContext context){
		Random r=new Random();
		return Math.abs(r.nextInt());
	}
	
	/**
	 * 生成long型随机数
	 * @param template
	 * @param context
	 * @return
	 */
	protected Long nextLong(Template template, TemplateContext context){
		Random r=new Random();
		return Math.abs(r.nextLong());
	}
	
	/**
	 * 生成float型随机数
	 * @param template
	 * @param context
	 * @return
	 */
	protected Float nextFloat(Template template, TemplateContext context){
		Random r=new Random();
		return Math.abs(r.nextFloat());
	}
	
	/**
	 * 生成double型随机数
	 * @param template
	 * @param context
	 * @return
	 */
	protected Double nextDouble(Template template, TemplateContext context){
		Random r=new Random();
		return Math.abs(r.nextDouble());
	}
	
	/**
	 * 生成String型随机数
	 * @param template
	 * @param context
	 * @return
	 */
	protected String nextUUID(Template template, TemplateContext context){
		return UUID.randomUUID().toString();
	}

}
