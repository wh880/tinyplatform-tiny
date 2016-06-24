package org.tinygroup.webservice.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("context-params")
public class ContextParams {

	@XStreamImplicit
	private List<ContextParam> contextParams;
	@XStreamImplicit
	private List<PastPattern> pastPatterns;
	@XStreamImplicit
	private List<SkipPattern> skipPatterns;

	public List<ContextParam> getContextParams() {
		if(contextParams==null){
			contextParams=new ArrayList<ContextParam>();
		}
		return contextParams;
	}

	public void setContextParams(List<ContextParam> contextParams) {
		this.contextParams = contextParams;
	}

	public List<PastPattern> getPastPatterns() {
		if(pastPatterns==null){
			pastPatterns=new ArrayList<PastPattern>();
		}
		return pastPatterns;
	}

	public void setPastPatterns(List<PastPattern> pastPatterns) {
		this.pastPatterns = pastPatterns;
	}

	public List<SkipPattern> getSkipPatterns() {
		if(skipPatterns==null){
			skipPatterns=new ArrayList<SkipPattern>();
		}
		return skipPatterns;
	}

	public void setSkipPatterns(List<SkipPattern> skipPatterns) {
		this.skipPatterns = skipPatterns;
	}
	
}
