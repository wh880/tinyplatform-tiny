/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.uiengine.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by luoguo on 2015/6/20.
 */
@XStreamAlias("compatibility-resource")
public class CompatibilityResource {
    @XStreamAlias("condition")
    @XStreamAsAttribute
    String condition;
    @XStreamAlias("css-resource")
    private String cssResource;
    @XStreamAlias("js-resource")
    private String jsResource;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCssResource() {
        return cssResource;
    }

    public void setCssResource(String cssResource) {
        this.cssResource = cssResource;
    }

    public String getJsResource() {
        return jsResource;
    }

    public void setJsResource(String jsResource) {
        this.jsResource = jsResource;
    }
}
