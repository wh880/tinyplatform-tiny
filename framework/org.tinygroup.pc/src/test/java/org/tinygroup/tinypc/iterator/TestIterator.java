package org.tinygroup.tinypc.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
	public static void main(String[] args) {
		List<String> s = new ArrayList<String>();
		s.add("a");
		s.add("b");
		Iterator<String> is = s.iterator();
		while(is.hasNext()){
			String str = is.next();
			System.out.println(str);
			//is.remove();
		}
		System.out.println(s.size());
	}
}
