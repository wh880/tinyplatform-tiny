package org.tinygroup.servicesample;

public class ServiceSample {
	public void read(SampleParam s) {
//		System.out.println("read don'e");
	}

	public SampleParam write(SampleParam s) {
		s.setAge(1);
		return s;
	}
}
