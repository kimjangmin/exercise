package com.example;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class Generator {
    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.example.gon.greenopen.db");
        Entity person = schema.addEntity("Person");
        person.addStringProperty("name").primaryKey();
        person.addIntProperty("age").notNull();

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }
}
