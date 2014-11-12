package org.tinygroup.bundlejar.component;

import org.tinygroup.bundlejar.BundleTestObject;
import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class BundleComponent implements ComponentInterface{
	private BundleTestObject object;
	private int i;
	public void execute(Context context) {
		System.out.println(i);
		System.out.println(object);
		System.out.println(object.getName());
		System.out.println(object.getNum());
	}
	public BundleTestObject getObject() {
		return object;
	}
	public void setObject(BundleTestObject object) {
		this.object = object;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}

}
