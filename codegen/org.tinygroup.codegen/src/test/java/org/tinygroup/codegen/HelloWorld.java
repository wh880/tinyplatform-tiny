package org.tinygroup.codegen;

import java.io.Serializable;


public class HelloWorld implements Serializable{
     
  public String sayHello(String name){
     if(name==null){
        name="haha";
     }
     return "hello"+name;
  }

     //sff实打实的
}