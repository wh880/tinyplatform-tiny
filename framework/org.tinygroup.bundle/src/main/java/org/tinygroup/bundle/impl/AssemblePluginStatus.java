/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES内核
 * 文件名称: AssamblePluginStatus.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */ 

package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.BundleManager;

/**
 * 功能说明: 
 * <p> 系统版本: v1.0<br>
 * 开发人员: renhui <br>
 * 开发时间: 2012-9-19 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class AssemblePluginStatus implements PluginStatus
{
	
	private String status="assemble";
	private BundleManager manager;

	/* (non-Javadoc)
	 * @see org.tinygroup.bundle.PluginStatus#actionStatus(org.tinygroup.bundle.BundleManager)
	 */
	public void actionStatus(BundleManager manager)
	{
		this.manager=manager;

	}

	/* (non-Javadoc)
	 * @see org.tinygroup.bundle.PluginStatus#getStatus()
	 */
	public String getStatus()
	{
		return status;
	}

}
