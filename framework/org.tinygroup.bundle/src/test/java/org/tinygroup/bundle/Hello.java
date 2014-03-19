package org.tinygroup.bundle;

public class Hello {
	String version;

	public Hello(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String sayHello(String name) {

		if (name == null) {
			name = "world.";
		}
		return version + ": hello, " + name;
	}
}
