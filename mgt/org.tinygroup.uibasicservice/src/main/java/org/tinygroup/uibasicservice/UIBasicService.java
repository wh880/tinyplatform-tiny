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
package org.tinygroup.uibasicservice;

import org.tinygroup.uiengine.config.UIComponent;
import org.tinygroup.uiengine.manager.UIComponentManager;

import java.util.List;

public class UIBasicService {
	private UIComponentManager uiManager;

	public UIComponentManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIComponentManager uiManager) {
		this.uiManager = uiManager;
	}

	public List<UIComponent> getUiComponents() {
		return uiManager.getUiComponents();
	}

	public UIComponent getUIComponent(String name) {
		return uiManager.getUIComponent(name);
	}

	public List<UIComponent> getHealthUiComponents() {
		return uiManager.getHealthUiComponents();
	}

	public String[] getComponentJsArray(String name) {
		return uiManager.getComponentJsArray(getUIComponent(name));
	}

	public String[] getComponentCssArray(String name) {
		return uiManager.getComponentCssArray(getUIComponent(name));
	}
}
