package org.tinygroup.database.config.sequence;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("sequences")
public class Sequences {

	@XStreamImplicit
	private List<Sequence> sequences;

	public List<Sequence> getSequences() {
		if(sequences==null){
			sequences=new ArrayList<Sequence>();
		}
		return sequences;
	}

	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}
	
}
