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
package org.tinygroup.springmvc.coc.impl;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springmvc.coc.ConventionComponentIdentifier;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author renhui
 *
 */
public class ConventionControllerIdentifier implements ConventionComponentIdentifier{
    private PathMatcher  pathMatcher = new AntPathMatcher();
    private List<String> pkgPatterns = null;

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public void setPkgPatterns(List<String> pkgPatterns) {
		this.pkgPatterns = pkgPatterns;
	}

	public List<String> getPackagePatterns() {
		if(CollectionUtil.isEmpty(pkgPatterns)){
			String prefix="**.web";
			String[] patterns = new String[] { prefix + ".**." + this.getHandlerType(),
	                prefix + "." + this.getHandlerType() };
	        pkgPatterns = Arrays.asList(patterns);
		}
        return pkgPatterns;
    }

    protected String getHandlerClassNamePattern(String pkgPattern) {
        return pkgPattern + ".*" + StringUtils.capitalize(getHandlerType());
    }

    public boolean isComponent(String className) {
        // ANT PATH MATCH
        List<String> pkgPatterns = this.getPackagePatterns();

        for (String pkgPattern : pkgPatterns) {
            boolean flag = pathMatcher
                .match(this.getHandlerClassNamePattern(pkgPattern), className);
            if (flag)
                return true;
        }
        return false;
    }


    protected String getHandlerType() {
        return "controller";
    }

}
