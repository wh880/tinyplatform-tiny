package org.tiny.seg.impl;

import org.tiny.seg.FoundEvent;


public class PrintFoundEvent implements FoundEvent {

	public void process(String word) {
		System.out.println(word);
	}
}
