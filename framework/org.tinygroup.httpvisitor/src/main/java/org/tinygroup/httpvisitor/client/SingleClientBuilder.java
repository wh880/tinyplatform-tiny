package org.tinygroup.httpvisitor.client;


public class SingleClientBuilder extends ClientBuilder<SingleClientBuilder>{

	public SingleClientBuilder(String templateId) {
		super(templateId);
	}

	protected SingleClientBuilder self() {
		return this;
	}

}
