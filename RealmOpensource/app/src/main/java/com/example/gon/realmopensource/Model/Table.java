package com.example.gon.realmopensource.Model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 김장민 on 2017-01-19.
 */

public class Table extends RealmObject {
    @PrimaryKey
    String name;
    int age;

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
    @Override
    public String toString() {
        return "Name = "+name +"\nAge = "+age;
    }
}
