package org.tinygroup.context2object.test.generator2.config;

import java.util.List;

import org.tinygroup.context2object.test.bean.CatInterface;

public class IntefaceObject {
	String name;
	List<CatInterface> cats;
	CatInterface cat;
	CatInterface[] catsArray;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CatInterface> getCats() {
		return cats;
	}
	public void setCats(List<CatInterface> cats) {
		this.cats = cats;
	}
	public CatInterface getCat() {
		return cat;
	}
	public void setCat(CatInterface cat) {
		this.cat = cat;
	}
	public CatInterface[] getCatsArray() {
		return catsArray;
	}
	public void setCatsArray(CatInterface[] catsArray) {
		this.catsArray = catsArray;
	}
	
	
}
