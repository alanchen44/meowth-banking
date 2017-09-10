package com.example.group_0715.bankapp_group_0715.adminactivity;

import java.io.Serializable;

/**
 * Created by Alan on 2017-08-29.
 */

public class Quadruple<Type1, Type2, Type3, Type4> implements Serializable{

    Type1 id;
    Type2 info;
    Type3 info2;
    Type4 info3;


    public Quadruple(Type1 id, Type2 recipient, Type3 msg, Type4 viewed) {
        this.id = id;
        this.info = recipient;
        this.info2 = msg;
        this.info3 = viewed;
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
    public Type4 getInfo3() {
        return this.info3;
    }
    public void setInfo3(Type4 info3) {
        this.info3 = info3;
    }

}
