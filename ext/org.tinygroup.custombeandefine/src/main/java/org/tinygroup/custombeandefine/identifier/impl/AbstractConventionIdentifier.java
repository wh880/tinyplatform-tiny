package org.tinygroup.custombeandefine.identifier.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.custombeandefine.identifier.ConventionComponentIdentifier;

/**
 *
 * @author renhui
 *
 */
public abstract class AbstractConventionIdentifier implements ConventionComponentIdentifier{
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
			String[] patterns = new String[] { "**." + this.getHandlerType()};
	        pkgPatterns = Arrays.asList(patterns);
		}
        return pkgPatterns;
    }

    protected String getHandlerClassNamePattern(String pkgPattern) {
        return pkgPattern + ".*" + StringUtils.capitalize(getHandlerType());
    }

    public boolean isComponent(String className) {
        List<String> pkgPatterns = this.getPackagePatterns();

        for (String pkgPattern : pkgPatterns) {
            boolean flag = pathMatcher
                .match(this.getHandlerClassNamePattern(pkgPattern), className);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    protected abstract String getHandlerType();

}
