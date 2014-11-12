package org.tinygroup.cepcorenetty;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.fileresolver.ChangeLisenter;
import org.tinygroup.fileresolver.FileResolver;

public class EventProcessorChangeLisenter implements ChangeLisenter{

	CEPCore cepcore;
	
	
	public CEPCore getCepcore() {
		return cepcore;
	}


	public void setCepcore(CEPCore cepcore) {
		this.cepcore = cepcore;
	}


	public void change(FileResolver resolver) {
		cepcore.refreshEventProcessors();
	}

}
