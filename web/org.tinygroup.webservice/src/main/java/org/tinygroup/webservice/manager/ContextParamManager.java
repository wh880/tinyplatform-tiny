package org.tinygroup.webservice.manager;

import java.util.List;

import org.tinygroup.webservice.config.ContextParams;
import org.tinygroup.webservice.config.PastPattern;
import org.tinygroup.webservice.config.SkipPattern;

/**
 * ws监听器参数过滤器
 * @author renhui
 *
 */
public interface ContextParamManager {
	
	String CONTEXT_PARAM_MANAGER_BEAN_NAME="contextParamManager";

    String XSTEAM_PACKAGE_NAME = "contextParam";
	
	public void addContextParams(ContextParams contextParams);
	
	
	public void removeContextParams(ContextParams contextParams);
	
	
	public List<ContextParams> getAllContextParams();
	
	public List<SkipPattern> getAllSkipPattens();
	
	public List<PastPattern> getAllPastPatterns();

}
