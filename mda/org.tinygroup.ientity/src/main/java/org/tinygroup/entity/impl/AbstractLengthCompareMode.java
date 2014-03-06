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
package org.tinygroup.entity.impl;

import org.tinygroup.commons.tools.ObjectUtil;
import org.tinygroup.context.Context;
import org.tinygroup.entity.EntityModelHelper;
import org.tinygroup.entity.util.ModelUtil;

public abstract class AbstractLengthCompareMode extends AbstractCompareMode {

	public static final int DEFAULT_LEVEL_LENGTH = 2;
	
	
	public boolean needValue() {
		return false;
	}

	public AbstractLengthCompareMode() {
		super();
	}

	
	protected Object formaterValue(Object value, String name, Context context) {
		Integer lengthLevel = null;
		if (context == null) {
			lengthLevel = DEFAULT_LEVEL_LENGTH;
		} else {
			String lengthLevelName = ModelUtil.getSpliceParamterName(name,
					EntityModelHelper.LEVEL_LENGTH);
			Object objectValue=context.get(lengthLevelName);
			if(objectValue!=null){
				lengthLevel = Integer.parseInt(String.valueOf(objectValue));
			}

		}
		lengthLevel = ObjectUtil.defaultIfNull(lengthLevel,
				DEFAULT_LEVEL_LENGTH);
		if (value != null) {
			int lengthValue = String.valueOf(value).length();
			lengthValue += lengthLevel;
			return lengthValue;
		}else{
			return lengthLevel;
		}
		
	}

}