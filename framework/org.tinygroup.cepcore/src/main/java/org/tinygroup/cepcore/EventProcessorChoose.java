package org.tinygroup.cepcore;

import java.util.List;

public interface EventProcessorChoose {
	
	int DEFAULT_WEIGHT = 10;
	EventProcessor choose(List<EventProcessor> processors);
}
