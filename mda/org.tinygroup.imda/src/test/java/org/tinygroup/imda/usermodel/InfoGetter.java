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
package org.tinygroup.imda.usermodel;

import org.tinygroup.imda.ModelInformationGetter;
import org.tinygroup.imda.config.CustomizeStageConfig;
import org.tinygroup.imda.validate.ValidateRule;

import java.util.List;
import java.util.Map;

public class InfoGetter implements ModelInformationGetter<CaseModel> {

	public String getId(CaseModel model) {
		return model.getId();
	}

	public String getName(CaseModel model) {
		return model.getName();
	}

	public String getCategory(CaseModel model) {
		return model.getPath();
	}

	public String getTitle(CaseModel model) {
		return model.getTitle();
	}

	public String getDescription(CaseModel model) {
		return model.getDes();
	}

	public Object getOperation(CaseModel model, String id) {
		return null;
	}

	public String getOperationType(CaseModel model, String id) {
		return null;
	}

	public String getOperationId(Object operation) {
		return null;
	}

	public List<String> getParamterList(CaseModel model, Object operation) {
		return null;
	}

	public CustomizeStageConfig getCustomizeStageConfig(CaseModel model, String operationId,
			String stageName) {
		return null;
	}

	public Map<String, List<ValidateRule>> getOperationValidateMap(
			CaseModel model, Object operation) {
		return null;
	}

}
