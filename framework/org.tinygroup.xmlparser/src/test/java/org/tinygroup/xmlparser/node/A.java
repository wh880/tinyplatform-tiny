package org.tinygroup.xmlparser.node;

/**
 * Created by luoguo on 14-3-5.
 */
public class A {
    private static final A instance=new A();
    private A(){

    }
    public static A getInstance(){
        return instance;
    }
}
