package org.tinygroup.webservice.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.webservice.config.ContextParams;
import org.tinygroup.webservice.config.PastPattern;
import org.tinygroup.webservice.config.SkipPattern;
import org.tinygroup.webservice.manager.ContextParamManager;

/**
 * 参数管理器实现类
 * @author renhui
 *
 */
public class ContextParamManagerImpl implements ContextParamManager{
	
	private List<ContextParams> contextParamList=new ArrayList<ContextParams>();

	public void addContextParams(ContextParams contextParams) {
		contextParamList.add(contextParams);
	}

	public void removeContextParams(ContextParams contextParams) {
		contextParamList.remove(contextParams);
	}

	public List<ContextParams> getAllContextParams() {
		return contextParamList;
	}

	public List<SkipPattern> getAllSkipPattens() {
		List<SkipPattern> skipPatterns=new ArrayList<SkipPattern>();
		for (ContextParams contextParams : contextParamList) {
			skipPatterns.addAll(contextParams.getSkipPatterns());
		}
		return skipPatterns;
	}

	public List<PastPattern> getAllPastPatterns() {
		List<PastPattern> pastPatterns=new ArrayList<PastPattern>();
		for (ContextParams contextParams : contextParamList) {
			pastPatterns.addAll(contextParams.getPastPatterns());
		}
		return pastPatterns;
	}

}
