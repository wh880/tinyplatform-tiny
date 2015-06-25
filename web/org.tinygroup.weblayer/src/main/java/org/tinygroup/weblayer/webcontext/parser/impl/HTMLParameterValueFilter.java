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
package org.tinygroup.weblayer.webcontext.parser.impl;

import org.tinygroup.support.BeanSupport;
import org.tinygroup.weblayer.webcontext.parser.upload.ParameterValueFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import static org.tinygroup.commons.tools.BasicConstant.EMPTY_STRING_ARRAY;
import static org.tinygroup.commons.tools.CollectionUtil.createHashMap;
import static org.tinygroup.commons.tools.ObjectUtil.defaultIfNull;

/**
 * 过滤参数。
 *
 * @author renhui
 */
public class HTMLParameterValueFilter extends BeanSupport implements ParameterValueFilter {
    private HTMLInputFilter          filter;
    private Map<String, Set<String>> allowed;
    private String[]                 deniedTags;
    private String[]                 selfClosingTags;
    private String[]                 needClosingTags;
    private String[]                 allowedProtocols;
    private String[]                 protocolAtts;
    private String[]                 removeBlanks;
    private String[]                 allowedEntities;

    
    protected void init() {
        allowed = createHashMap(); // allowed tags and attrs
        deniedTags = defaultIfNull(deniedTags, EMPTY_STRING_ARRAY);
        selfClosingTags = defaultIfNull(selfClosingTags, EMPTY_STRING_ARRAY);
        needClosingTags = defaultIfNull(needClosingTags, EMPTY_STRING_ARRAY);
        allowedProtocols = defaultIfNull(allowedProtocols, EMPTY_STRING_ARRAY);
        protocolAtts = defaultIfNull(protocolAtts, EMPTY_STRING_ARRAY);
        removeBlanks = defaultIfNull(removeBlanks, EMPTY_STRING_ARRAY);
        allowedEntities = defaultIfNull(allowedEntities, EMPTY_STRING_ARRAY);

        filter = new HTMLInputFilter(allowed, deniedTags, selfClosingTags, needClosingTags, allowedProtocols,
                                     protocolAtts, removeBlanks, allowedEntities);
    }

    public boolean isFiltering(HttpServletRequest request) {
        return true;
    }

    public String filter(String key, String value, boolean isHtml) {
        assertInitialized();

        if (value == null) {
            return null;
        }

        return filter.filter(value, isHtml);
    }
}
