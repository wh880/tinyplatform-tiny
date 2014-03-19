package org.tinygroup.bundle.plugin;

public class HelloAll {
	Hello hello;
	String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HelloAll(String version) {
		this.version = version;
	}

	public Hello getHello() {
		return hello;
	}

	public void setHello(Hello hello) {
		this.hello = hello;
		hello.setVersion(version);
	}

	public String sayHello(String name) {
		return hello.sayHello(name);
	}
}
