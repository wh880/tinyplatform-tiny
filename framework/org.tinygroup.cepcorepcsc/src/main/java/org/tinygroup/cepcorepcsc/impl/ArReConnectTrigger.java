package org.tinygroup.cepcorepcsc.impl;

import java.rmi.RemoteException;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.ConnectTrigger;

public class ArReConnectTrigger implements ConnectTrigger{
	private ArOperator operator ;
	private Logger logger = LoggerFactory.getLogger(ArReConnectTrigger.class);
	
	public ArReConnectTrigger(ArOperator operator){
		this.operator = operator;
	}
	public void deal() {
		try {
			operator.reg();
		} catch (RemoteException e) {
			logger.errorMessage("向远端服务器发起重新注册时出错",e);
		}
	}

	public String getType() {
		return ConnectTrigger.REREG;
	}

}
