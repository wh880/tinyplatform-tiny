package org.tinygroup.weixinpay.result;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.weixin.common.ToServerResult;

/**
 * 下载账单的结果
 * @author yancheng11334
 *
 */
public class DownloadBillResult implements ToServerResult{

	private List<BillField> dataFieldList = new ArrayList<BillField>();
	
	private BillField totalField;

	public List<BillField> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<BillField> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public BillField getTotalField() {
		return totalField;
	}

	public void setTotalField(BillField totalField) {
		this.totalField = totalField;
	}
	
	
}
