package com.osteching.litemongo;

import com.osteching.litemongo.annotation.Entity;
import com.osteching.litemongo.annotation.Field;

@Entity(collection = "person")
public class Person {
    
    @Field(key="name")
    private String name;

    @Field(key="age")
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
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

}
