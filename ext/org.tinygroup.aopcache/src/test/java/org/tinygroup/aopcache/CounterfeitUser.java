package org.tinygroup.aopcache;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangwy11342 on 2016/4/7.
 * 假冒的user
 */
public class CounterfeitUser implements Serializable{

    private static final long serialVersionUID = 2801918479624727325L;
    /**
     *
     */
    int id;
    String name;
    int age;
    Date birth;

    public CounterfeitUser() {
    }

    public CounterfeitUser(int id, String name, int age, Date birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
