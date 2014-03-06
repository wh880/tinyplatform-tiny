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
package org.tinygroup.metadata.util;

import java.util.List;

import org.tinygroup.metadata.bizdatatype.BusinessTypeProcessor;
import org.tinygroup.metadata.config.PlaceholderValue;
import org.tinygroup.metadata.config.bizdatatype.BusinessType;
import org.tinygroup.metadata.config.stddatatype.StandardType;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.stddatatype.StandardTypeProcessor;
import org.tinygroup.metadata.stdfield.StandardFieldProcessor;
import org.tinygroup.springutil.SpringUtil;

public final class MetadataUtil {
	public static final String METADATA_XSTREAM = "metadata";
	public static final String STDFIELDPROCESSOR_BEAN = "standardFieldProcessor";
	public static final String STANDARDTYPEPROCESSOR_BEAN = "standardTypeProcessor";
	public static final String BUSINESSTYPEPROCESSOR_BEAN = "businessTypeProcessor";
	public static final String CONSTANTPROCESSOR_BEAN = "constantProcessor";
	public static final String ERRORMESSAGEPROCESSOR_BEAN = "errorMessageProcessor";

	private MetadataUtil() {
	}

	public static String passNull(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	public static String formatType(String type,
			List<PlaceholderValue> placeholderValueList) {
		String result = type;
		if (placeholderValueList != null) {
			for (PlaceholderValue placeholderValue : placeholderValueList) {
				result = result.replaceAll(
						"[$][{]" + placeholderValue.getName() + "[}]",
						placeholderValue.getValue());
			}
		}
		return result;
	}

	public static StandardField getStandardField(String fieldId) {
		StandardFieldProcessor standardFieldProcessor = SpringUtil.getBean(MetadataUtil.STDFIELDPROCESSOR_BEAN);
		return standardFieldProcessor.getStandardField(fieldId);
	}

	public static String getStandardFieldType(String stdFieldId, String type) {
		StandardFieldProcessor standardFieldProcessor = SpringUtil.getBean(MetadataUtil.STDFIELDPROCESSOR_BEAN);
		String datatype = standardFieldProcessor.getType(stdFieldId, type);
		return datatype;
	}

	public static StandardType getStandardType(String fieldId) {
		StandardField field = getStandardField(fieldId);
		BusinessTypeProcessor businessTypeProcessor = SpringUtil
				.getBean(MetadataUtil.BUSINESSTYPEPROCESSOR_BEAN);
		BusinessType businessType = businessTypeProcessor
				.getBusinessTypes(field.getTypeId());
		StandardTypeProcessor standardTypeProcessor = SpringUtil
				.getBean(MetadataUtil.STANDARDTYPEPROCESSOR_BEAN);
		return standardTypeProcessor.getStandardType(businessType.getTypeId());
	}
	
	public static StandardType getStandardType(StandardField field) {
		BusinessTypeProcessor businessTypeProcessor = SpringUtil
				.getBean(MetadataUtil.BUSINESSTYPEPROCESSOR_BEAN);
		BusinessType businessType = businessTypeProcessor
				.getBusinessTypes(field.getTypeId());
		StandardTypeProcessor standardTypeProcessor = SpringUtil
				.getBean(MetadataUtil.STANDARDTYPEPROCESSOR_BEAN);
		return standardTypeProcessor.getStandardType(businessType.getTypeId());
	}
}
