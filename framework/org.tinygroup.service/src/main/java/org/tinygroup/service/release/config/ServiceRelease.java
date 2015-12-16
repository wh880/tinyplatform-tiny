/**
 * 
 */
package org.tinygroup.service.release.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author yanwj
 *
 */
@XStreamAlias("servicerelease")
public class ServiceRelease {

	/**
	 * 黑名单
	 * 
	 */
	@XStreamAlias("excludes")
	ReleaseItem excludes;
	
	/**
	 * 白名单
	 * 
	 */
	@XStreamAlias("includes")
	ReleaseItem includes;

	public ReleaseItem getExcludes() {
		return excludes;
	}

	public void setExcludes(ReleaseItem excludes) {
		this.excludes = excludes;
	}

	public ReleaseItem getIncludes() {
		return includes;
	}

	public void setIncludes(ReleaseItem includes) {
		this.includes = includes;
	}

}
