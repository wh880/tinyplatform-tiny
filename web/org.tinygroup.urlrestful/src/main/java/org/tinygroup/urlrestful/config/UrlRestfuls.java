package org.tinygroup.urlrestful.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("url-restfuls")
public class UrlRestfuls {
	
	@XStreamImplicit
	private List<UrlRestful> urlRestfuls;

	public List<UrlRestful> getUrlRestfuls() {
		if(urlRestfuls==null){
			urlRestfuls=new ArrayList<UrlRestful>();
		}
		return urlRestfuls;
	}

	public void setUrlRestfuls(List<UrlRestful> urlRestfuls) {
		this.urlRestfuls = urlRestfuls;
	}

}
