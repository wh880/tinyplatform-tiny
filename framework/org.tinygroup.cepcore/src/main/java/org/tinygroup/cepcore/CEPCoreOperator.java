/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.cepcore;

import java.util.List;

import org.tinygroup.xmlparser.node.XmlNode;

public interface CEPCoreOperator {
	
	/**
	 * 启动节点
	 * @param cep
	 */
	void startCEPCore(CEPCore cep);

	/**
	 * 停止节点
	 * @param cep
	 */
	void stopCEPCore(CEPCore cep);

	/**
	 * 设置CEPCore
	 * @param cep
	 */
	void setCEPCore(CEPCore cep);

	/**
	 * 设置参数
	 * @param cep
	 */
	void setParam(XmlNode node);
	
	/**
	 * 查询与SC之间的链接情况的状态
	 * @return 结果状态列表,单条状态数据为SCIp:SCPort:status,status为ture或者false
	 * Node Operator返回的为List<String>
	 * SC   Operator返回为null
	 */
	List<String> getConnectStatus();
	
}
