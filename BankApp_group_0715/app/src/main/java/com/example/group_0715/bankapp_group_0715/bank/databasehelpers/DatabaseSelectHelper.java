package com.example.group_0715.bankapp_group_0715.bank.databasehelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.group_0715.bankapp_group_0715.bank.accountfactory.AccountFactory;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.bank.userfactory.UserFactory;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.example.group_0715.bankapp_group_0715.R.id.userId;

/**
 * Created by Alan on 2017-07-29.
 */

public class DatabaseSelectHelper extends DatabaseDriverA {

    public DatabaseSelectHelper(Context context) {
        super(context);
    }

    public List<Integer> getRoleList() {
        List<Integer> roleIdList = new ArrayList<>();
        Cursor cursor = super.getRoles();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                roleIdList.add(cursor.getInt(cursor.getColumnIndex("ID")));
                cursor.moveToNext();
            }
            return roleIdList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public String getRole(int id) {
        return super.getRole(id);
    }

    @Override
    public int getUserRole(int userId) {
        return super.getUserRole(userId);
    }

    public List<User> getUsersDetailsList(Context context) {
        List<User> usersDetailsList = new ArrayList<>();
        Cursor cursor = super.getUsersDetails();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int curId = cursor.getInt(cursor.getColumnIndex("ID"));
                User user = this.getUserDetailsInfo(curId, context);
                usersDetailsList.add(user);
                cursor.moveToNext();
            }
            return usersDetailsList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public User getUserDetailsInfo(int userId, Context context) {
        Cursor cursor = super.getUserDetails(userId);
        try {
            cursor.moveToFirst();
            if(!cursor.isAfterLast()) {
                int type = cursor.getInt(cursor.getColumnIndex("ROLEID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                int age = cursor.getInt(cursor.getColumnIndex("AGE"));
                String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));

                String typeString = getRole(type);

                UserFactory uf = new UserFactory();
                User user = uf.makeUserBuilderNoAuthenticate(typeString, userId, name, age, address, context);
                return user;
            } else {
                return null;
            }
        }  finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public String getPassword(int userId) {
        return super.getPassword(userId);
    }

    public List<Integer> getAccountIdsList(int userId) {
        Cursor cursor = super.getAccountIds(userId);
        List<Integer> accIdsList = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int curId = cursor.getInt(cursor.getColumnIndex("ACCOUNTID"));
                accIdsList.add(curId);
                cursor.moveToNext();
            }
            return accIdsList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public Account getAccountDetails(int accountId, Context context) {
        Cursor cursor = super.getAccountDetails(accountId);
        try {
            cursor.moveToFirst();
            if(!cursor.isAfterLast()) {
                int type = cursor.getInt(cursor.getColumnIndex("TYPE"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                BigDecimal balance = getBalance(accountId);
                String typeString = getAccountTypeName(type);
                AccountFactory af = new AccountFactory();
                Account acc = af.makeAccount(typeString, accountId, name, balance, context);
                return acc;
            } else {
                return null;
            }
        }
         finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    @Override
    public BigDecimal getBalance(int accountId) {
        return super.getBalance(accountId);
    }

    @Override
    public int getAccountType(int accountId) {
        return super.getAccountType(accountId);
    }

    @Override
    public String getAccountTypeName(int accountTypeId) {
        return super.getAccountTypeName(accountTypeId);
    }

    public List<Integer> getAccountTypesIdList() {
        Cursor cursor = super.getAccountTypesId();
        List<Integer> accTypeIdsList = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int curId = cursor.getInt(cursor.getColumnIndex("ID"));
                accTypeIdsList.add(curId);
                cursor.moveToNext();
            }
            return accTypeIdsList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public BigDecimal getInterestRate(int accountType) {
        return super.getInterestRate(accountType);
    }

    public List<Integer> getAllMessagesList(int userId) {
        Cursor cursor = super.getAllMessages(userId);
        List<Integer> msgList = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int curId = cursor.getInt(cursor.getColumnIndex("ID"));
                msgList.add(curId);
                cursor.moveToNext();
            }
            return msgList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    @Override
    public String getSpecificMessage(int messageId){
        return super.getSpecificMessage(messageId);
    }

    @Override
    public int getMessageViewed(int messageId){
        return super.getMessageViewed(messageId);
    }

    @Override
    public int getMessageRecipient(int messsageId) {
        return super.getMessageRecipient(messsageId);
    }

    public List<int []> getAllUserAccountRelationships() {
        Cursor cursor = super.getAllUserAccounts();
        List<int []> userAccList = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int userId = cursor.getInt(cursor.getColumnIndex("USERID"));
                int accId = cursor.getInt(cursor.getColumnIndex("ACCOUNTID"));
                int userAcc [] = {userId, accId};
                userAccList.add(userAcc);
                cursor.moveToNext();
            }
            return userAccList;
        } finally {
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

    }

}
