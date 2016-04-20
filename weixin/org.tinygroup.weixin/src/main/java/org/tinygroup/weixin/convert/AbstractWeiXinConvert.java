package org.tinygroup.weixin.convert;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.common.UrlConfig;

public abstract class AbstractWeiXinConvert implements WeiXinConvert{
   
    private int priority;
    
    protected Class<?> clazz;
    
    public AbstractWeiXinConvert(Class<?> clazz){
    	this.clazz = clazz;
    }
    
    public Class<?> getCalssType(){
    	return clazz;
    }
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(WeiXinConvert o) {
		if (o.getPriority() == getPriority()) {
            return 0;
        } else if (o.getPriority() < getPriority()) {
            return 1;
        } else {
            return -1;
        }
	} 
	
	/**
	 * 检查返回结果是否和当前处理器匹配
	 * @param <INPUT>
	 * @param input
	 * @param context
	 * @return
	 */
	protected <INPUT> boolean checkResultType(INPUT input,WeiXinContext context){
		if(getWeiXinConvertMode()!=WeiXinConvertMode.SEND || context==null){
		   return true;
		}
		UrlConfig config = context.get(UrlConfig.DEFAULT_CONTEXT_NAME);
		if(config!=null && !StringUtil.isEmpty(config.getResultTypes())){
		   String [] types = config.getResultTypes().split(",");
		   String name = getCalssType().getName();
		   for(String type:types){
			   if(type.equals(name)){
				  return true;
			   }
		   }
		   return false;
		}
		return true;
	}

}
