package org.tinygroup.weixin.convert;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;

/**
 * 抽象的解析集合类
 * @author yancheng11334
 *
 */
public abstract class AbstractParser {

	protected List<WeiXinConvert> convertList = new ArrayList<WeiXinConvert>();
	
	public void addWeiXinConvert(WeiXinConvert convert){
		if(!convertList.contains(convert)){
			convertList.add(convert);
			java.util.Collections.sort(convertList);
		}
	}
	
	public void removeWeiXinConvert(WeiXinConvert convert){
		if(convertList.contains(convert)){
		   convertList.remove(convert);
		   java.util.Collections.sort(convertList);
		}
	}
	
	/**
	 * 判断转换器的类型是否匹配
	 * @param convert
	 * @param mode
	 * @return
	 */
	public boolean checkConvertMode(WeiXinConvert convert,WeiXinConvertMode mode){
		if(mode==null || mode== WeiXinConvertMode.ALL || convert.getWeiXinConvertMode() == WeiXinConvertMode.ALL){
		   return true;
		}
		return convert.getWeiXinConvertMode()==mode;
	}
	
	public abstract <T> T parse(String result,WeiXinContext context,WeiXinConvertMode mode);
}
