package com.example.group_0715.bankapp_group_0715.bank.databasehelpers;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;

/**
 * Created by Alan on 2017-08-27.
 */

public class DatabaseDeleteHelper extends DatabaseDriverA {

    public DatabaseDeleteHelper(Context context) {
        super(context);
    }

    @Override
    public void deleteAllRoles() {
        super.deleteAllRoles();
    }

    @Override
    public void deleteAllAccountTypes() {
        super.deleteAllAccountTypes();
    }

    @Override
    public void deleteAllPasswords() {
        super.deleteAllPasswords();
    }

    @Override
    public void deleteAllUsers() {
        super.deleteAllUsers();
    }

    @Override
    public void deleteAllAccounts() {
        super.deleteAllAccounts();
    }

    @Override
    public void deleteAllUserAccounts() {
        super.deleteAllUserAccounts();
    }

    @Override
    public void deleteAllMessages() {
        super.deleteAllMessages();
    }


}
