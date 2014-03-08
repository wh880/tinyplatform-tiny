/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */

package org.tinygroup.jspengine.compiler;

import javax.servlet.jsp.tagext.*;

/**
 * TagInfo extension used by tag handlers that are implemented via tag files.
 * This class provides access to the name of the Map used to store the
 * dynamic attribute names and values passed to the custom action invocation.
 * This information is used by the code generator.
 */
class JasperTagInfo extends TagInfo {

    private String dynamicAttrsMapName;

    public JasperTagInfo(String tagName,
			 String tagClassName,
			 String bodyContent,
			 String infoString,
			 TagLibraryInfo taglib,
			 TagExtraInfo tagExtraInfo,
			 TagAttributeInfo[] attributeInfo,
			 String displayName,
			 String smallIcon,
			 String largeIcon,
			 TagVariableInfo[] tvi,
			 String mapName) {

	super(tagName, tagClassName, bodyContent, infoString, taglib,
	      tagExtraInfo, attributeInfo, displayName, smallIcon, largeIcon,
	      tvi);
	this.dynamicAttrsMapName = mapName;
    }

    public String getDynamicAttributesMapName() {
	return dynamicAttrsMapName;
    }

    public boolean hasDynamicAttributes() {
        return dynamicAttrsMapName != null;
    }
}