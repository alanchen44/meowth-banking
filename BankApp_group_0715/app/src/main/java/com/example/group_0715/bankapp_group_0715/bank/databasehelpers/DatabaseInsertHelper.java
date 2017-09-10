package com.example.group_0715.bankapp_group_0715.bank.databasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;

import java.math.BigDecimal;

import static android.R.attr.name;
import static android.R.attr.password;
import static com.example.group_0715.bankapp_group_0715.R.string.balance;

/**
 * Created by Alan on 2017-07-29.
 */

public class DatabaseInsertHelper extends DatabaseDriverA {

//    private DatabaseDriverA db;

    public DatabaseInsertHelper(Context context) {
  //      db = new DatabaseDriverA(context);
        super(context);
    }

    public long insertRole(String role) {
     /*   SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", role);
        return sqLiteDatabase.insert("ROLES", null, contentValues);*/
     return super.insertRole(role);
    }

    public long insertNewUser(String name, int age, String address, int roleId, String password) {
        return super.insertNewUser(name, age, address, roleId, password);
    }

    public long insertNewUserWithHashedPassword(String name, int age, String address, int roleId, String password) {
        return super.insertNewUserWithHashedPassword(name, age, address, roleId, password);
    }

    public long insertAccountType(String name, BigDecimal interestRate) {
        return super.insertAccountType(name, interestRate);
    }

    public long insertAccount(String name, BigDecimal balance, int typeId) {
        return super.insertAccount(name, balance, typeId);
    }

    public long insertUserAccount(int userId, int accountId) {
        return super.insertUserAccount(userId,accountId);
    }

    public long insertMessage(int userId, String message) {
        return super.insertMessage(userId, message);
    }

}
