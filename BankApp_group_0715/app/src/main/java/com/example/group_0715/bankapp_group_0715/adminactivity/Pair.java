package com.example.group_0715.bankapp_group_0715.adminactivity;

import java.io.Serializable;

/**
 * Created by Alan on 2017-08-27.
 */

public class Pair<Type1, Type2> implements Serializable{

    Type1 id;
    Type2 info;

    public Pair(Type1 id, Type2 msg) {
        this.id = id;
        this.info = msg;
    }

    public Type1 getId() {
        return this.id;
    }

    public void setId(Type1 id) {
        this.id = id;
    }
    public Type2 getInfo() {
        return this.info;
    }
    public void setInfo(Type2 msg) {
        this.info = msg;
    }
}
