package org.tinygroup.cepcore.impl;

import java.util.List;

import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcore.EventProcessorChoose;

public class WeightChooser implements EventProcessorChoose {

	public EventProcessor choose(List<EventProcessor> processors) {
		int totalWeight = 0;
		for (EventProcessor eventProcessor : processors) {
			int wight = eventProcessor.getWeight();
			if (wight == 0) {
				wight = DEFAULT_WEIGHT;
			}
			totalWeight +=  wight;
		}
		int random = (int) (Math.random() * totalWeight);
		for (EventProcessor eventProcessor : processors) {
			int wight = eventProcessor.getWeight();
			if (wight == 0) {
				wight = DEFAULT_WEIGHT;
			}
			random -= wight;
			if(random<=0){
				return eventProcessor;
			}
		}
		//不会到达
		return processors.get(0);
	}

}
