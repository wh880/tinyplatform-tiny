package org.tinygroup.cepcore;

public interface EventProcessorRegisterTrigger {
	void trigger(EventProcessor processor,CEPCore core);
}
