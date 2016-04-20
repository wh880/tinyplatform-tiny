package org.tinygroup.weixinpay.convert.text;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.text.AbstractTextConvert;
import org.tinygroup.weixin.exception.WeiXinException;
import org.tinygroup.weixinpay.result.BillField;
import org.tinygroup.weixinpay.result.DownloadBillResult;

public class DownloadBillResultConvert extends AbstractTextConvert{

	public DownloadBillResultConvert() {
		super(DownloadBillResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(String input, WeiXinContext context) {
		return input.indexOf("总交易单数,总交易额")>-1;
	}

	@SuppressWarnings("unchecked")
	protected <OUTPUT> OUTPUT convertString(String input, WeiXinContext context) {
		String[] lines = input.split("\r\n");
		if(lines==null || lines.length<4){
		   throw new WeiXinException("微信报文的格式不正确");
		}
		DownloadBillResult result = new DownloadBillResult();
		//处理列表数据
		String[] names = lines[0].split(",");
		for(int i=1;i<lines.length-2;i++){
			BillField field = new BillField();
			//忽略第一个分隔符
			String[] values = getValues(lines[i]);
			for(int j=0;j<names.length;j++){
				String name = names[j];
			    String value = values[j];
			    if(value.endsWith(",")){
			       value = value.substring(0, value.length()-1);
			    }
			    field.setValue(name, value);
			}
			 result.getDataFieldList().add(field);
		}
		//处理汇总数据
		String[] tnames = lines[lines.length-2].split(",");
		String[] tvalues = getValues(lines[lines.length-1]);
		BillField totalField = new BillField();
		for(int j=0;j<tnames.length;j++){
			String name = tnames[j];
		    String value = tvalues[j];
		    if(value.endsWith(",")){
			   value = value.substring(0, value.length()-1);
			}
		    totalField.setValue(name, value);
		}
		result.setTotalField(totalField);
		return (OUTPUT) result;
	}
	
	private String[] getValues(String line){
		line = line.substring(1, line.length());
		return line.split("`");
	}

}
