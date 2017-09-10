package com.example.group_0715.bankapp_group_0715.adminactivity;

import java.io.Serializable;

/**
 * Created by Alan on 2017-08-28.
 */

public class Triple<Type1, Type2, Type3> implements Serializable{

    Type1 id;
    Type2 info;
    Type3 info2;

    public Triple(Type1 id, Type2 name, Type3 interest) {
        this.id = id;
        this.info = name;
        this.info2 = interest;
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
    public Type3 getInfo2() {
        return this.info2;
    }
    public void setInfo2(Type3 info2) {
        this.info2 = info2;
    }

}
