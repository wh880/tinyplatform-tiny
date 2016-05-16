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
package org.tinygroup.flow.exception.errorcode;


public class FlowExceptionErrorCode {
	 /**
     * 流程节点名称非空校验
     */
    public static final String FLOW_NODE_NAME_VALIDATE_EXCEPTION = "0TE12"+"0081"+"001";
    
    /**
     * 流程节点参数校验失败异常
     */
    public static final String FLOW_PROPERTY_VALIDATE_EXCEPTION = "0TE12"+"0081"+"002";

    /**
     * 未找到后续节点
     */
    public static final String FLOW_NEXT_NODE_NOT_FOUND_EXCEPTION = "0TE12"+"0081"+"003";
}
