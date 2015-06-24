/*
 * Alipay.com Inc. Copyright (c) 2004-2105 All Rights Reserved.
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
