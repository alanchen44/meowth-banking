package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseDeleteHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.bank.security.PasswordHelpers;
import com.example.group_0715.bankapp_group_0715.bank.userfactory.UserFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.password;
import static android.R.id.list;
import static com.example.group_0715.bankapp_group_0715.R.string.users;

/**
 * Created by Alan on 8/27/2017.
 */

public class AdminSerializeOrDeserializeDatabaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_serialize_selection_activity);
    }

    public void performSerialization(View view) {
        boolean serializeSuccess;

        // get all the data that needs to be serialized
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> allUsers = select.getUsersDetailsList(this);
        // int [] has 2 elements: first is userID, second element is accID
        List<int []> allUserAcc = select.getAllUserAccountRelationships();
        select.close();
        List<Account> allAcc = getAllaccount();
        List<Quadruple> allMsg = getAllMessages();
        List<Pair> allRoles = getAllRoles();
        List<Triple> allAccTypes = getAllAccTypes();
        List<Pair> allUnHashedPasswords = getAllPasswords();


        // put all lists into a single array for serialization
        List [] serialArray = {allUsers, allAcc, allUserAcc, allMsg, allRoles, allAccTypes, allUnHashedPasswords};
        // perform serialization
        // write the file into the internal storage
        try {
            Context context = getApplicationContext();
            FileOutputStream file = context.openFileOutput("SerializeFile.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(serialArray);
            oos.close();
            file.close();
            serializeSuccess = true;
        } catch (IOException e) {
            serializeSuccess = false;
        }

        String successTitle = "Success";
        String successMessage = "Serialization complete";
        if (!serializeSuccess) {
            successTitle = "Error";
            successMessage = "Unable to serialize.";
        }
        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        successBox.setMessage(successMessage);
        successBox.setTitle(successTitle);
        successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        successBox.setCancelable(false);
        successBox.create().show();
    }

    public void performDeserialization(View view) {
        int deserializeSuccess = -1;

        try {
            Context context = getApplicationContext();
            FileInputStream file = context.openFileInput("SerializeFile.ser");
            ObjectInputStream ois = new ObjectInputStream(file);
            List [] serialArray = (List [])ois.readObject();
            ois.close();
            file.close();

            //Delete contents in database and insert all serialized data into db
            // REFERENCE: List [] serialArray =
            // {allUsers, allAcc, allUserAcc, allMsg, allRoles, allAccTypes, allUnHashedPasswords};
            DatabaseDeleteHelper delete = new DatabaseDeleteHelper(this);
            delete.deleteAllAccounts();
            delete.deleteAllMessages();
            delete.deleteAllPasswords();
            delete.deleteAllUsers();
            delete.deleteAllUserAccounts();
            delete.deleteAllRoles();
            delete.deleteAllAccountTypes();
            delete.close();

            List<User> allUsers = serialArray[0];
            List<Account> allAcc = serialArray[1];
            List<int []> allUserAcc = serialArray[2];
            List<Quadruple> allMsg = serialArray[3];
            List<Pair> allRoles = serialArray[4];
            List<Triple> allAccTypes = serialArray[5];
            List<Pair> allPasswords = serialArray[6];

            insertRoles(allRoles);
            insertUsers(allUsers, allPasswords);
            insertAccTypes(allAccTypes);
            insertAccount(allAcc);
            insertUserAccountRelationship(allUserAcc);
            insertMessages(allMsg);
            deserializeSuccess = 0;

        } catch (IOException e) {
            deserializeSuccess = 1;
        } catch (ClassNotFoundException e) {
            deserializeSuccess = 2;
        }

        String successTitle;
        String successMessage;

        if (deserializeSuccess == 0) {
            successTitle = "Success";
            successMessage = "Deserialization complete";
        } else if (deserializeSuccess == 1) {
            successTitle = "Error";
            successMessage = "Unable to deserialize (IOException has been thrown).";
        } else if (deserializeSuccess == 2) {
            successTitle = "Error";
            successMessage = "Unable to deserialize (ClassNotFoundException has been thrown).";
        } else {
            successTitle = "Error";
            successMessage = "Unable to deserialize (An unknown error has occurred).";
        }
        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        successBox.setMessage(successMessage);
        successBox.setTitle(successTitle);
        successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        successBox.setCancelable(false);
        successBox.create().show();
    }

    private List<Account> getAllaccount(){
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Account> result = new ArrayList<>();
        boolean hasNext = true;
        int currentAccountId =1;
        while(hasNext){
            Account account = select.getAccountDetails(currentAccountId, this);
            if(account instanceof Account && account != null){
                result.add(account);
            }else{
                hasNext = false;
            }
            currentAccountId += 1;
        }
        select.close();
        return result;

    }

    private List<Quadruple> getAllMessages() {
        List<Quadruple> allMessages = new ArrayList<>();

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> userIdList = select.getUsersDetailsList(this);
        for (User curUser : userIdList) {
            int curUserId = curUser.getId();
            List<Integer> curUserMsgList = select.getAllMessagesList(curUserId);
            for (int msgId : curUserMsgList) {
                int recipientId = select.getMessageRecipient(msgId);
                String message = select.getSpecificMessage(msgId);
                int viewedStatus = select.getMessageViewed(msgId);
                Quadruple<Integer, Integer, String, Integer> msgQuad = new Quadruple<>(msgId, recipientId, message, viewedStatus);
                allMessages.add(msgQuad);
            }
        }
        select.close();
        return allMessages;
    }

    private List<Pair> getAllRoles() {
        List<Pair> result = new ArrayList<>();

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> allRoleId = select.getRoleList();

        for (int id : allRoleId) {
            String roleString = select.getRole(id);
            Pair<Integer, String> rolePair = new Pair<>(id, roleString);
            result.add(rolePair);
        }
        select.close();
        return result;
    }

    private List<Triple> getAllAccTypes() {
        List<Triple> result = new ArrayList<>();

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> allAccTypeId = select.getAccountTypesIdList();

        for (int id : allAccTypeId) {
            String accTypeString = select.getAccountTypeName(id);
            BigDecimal interest = select.getInterestRate(id);
            Triple<Integer, String, BigDecimal> accTypeTriple = new Triple<>(id, accTypeString, interest);
            result.add(accTypeTriple);
        }
        select.close();
        return result;
    }

    private List<Pair> getAllPasswords() {
        List<Pair> result = new ArrayList<>();

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> allUsers = select.getUsersDetailsList(this);

        for (User user : allUsers) {
            int curId = user.getId();
            String passHashed = select.getPassword(curId);
            Pair<Integer, String> passPair = new Pair<>(curId, passHashed);
            result.add(passPair);
        }
        select.close();
        return result;
    }


    private void insertRoles(List<Pair> roles) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int current = 1;
        int size = roles.size();
        while(current <= size) {
            for (Pair curPair : roles) {
                int curId = (int) curPair.getId();
                if (curId == current) {
                    String roleString = curPair.getInfo().toString();
                    insert.insertRole(roleString);
                    current += 1;
                }
            }
        }
        insert.close();

        // update rolemap so it matches the deserialized data
        RoleMap rm = RoleMap.getInstance(this);
        rm.updateRoleMap(this);
    }

    private void insertAccTypes(List<Triple> accTypes) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int current = 1;
        int size = accTypes.size();
        while(current <= size) {
            for (Triple curTriple : accTypes) {
                int curId = (int) curTriple.getId();
                if (curId == current) {
                    String accTypeString = curTriple.getInfo().toString();
                    BigDecimal interest = new BigDecimal(curTriple.getInfo2().toString());
                    insert.insertAccountType(accTypeString, interest);
                    current += 1;
                }
            }
        }
        insert.close();

        // update accTypeMap so it matches the deserialized data
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        accTypeMap.updateAccountMap(this);
    }

    private void insertMessages(List<Quadruple> messages) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int current = 1;
        int size = messages.size();
        while(current <= size) {
            for (Quadruple curQuad : messages) {
                int curId = (int) curQuad.getId();
                if (curId == current) {
                    int recipientId = (int)curQuad.getInfo();
                    String msg = curQuad.getInfo2().toString();
                    insert.insertMessage(recipientId, msg);
                    // change status to viewed if already viewed
                    int viewedStatus = (int) curQuad.getInfo3();
                    if (viewedStatus == 1) {
                        DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
                        update.updateUserMessageState(curId);
                        update.close();
                    }
                    current += 1;
                }
            }
        }
        insert.close();
    }

    private void insertUsers(List<User> users, List<Pair> userPasswords) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int current = 1;
        int size = users.size();
        while(current <= size) {
            for (User curUser : users) {
                int curId = curUser.getId();
                if (curId == current) {
                    String name = curUser.getName();
                    int age = curUser.getAge();
                    String address = curUser.getAddress();
                    int roleId = curUser.getRoleId();
                    String password = findUserPassword(curId, userPasswords);
                    if (password != null) {
                        insert.insertNewUserWithHashedPassword(name, age, address, roleId, password);
                    }
                    current += 1;
                }
            }
        }
        insert.close();
    }

    private String findUserPassword(int userId, List<Pair> userPasswords) {
        for (Pair curPair : userPasswords) {
            int curId = (int) curPair.getId();
            if (curId == userId) {
                return curPair.getInfo().toString();
            }
        }
        return null;
    }

    private void insertAccount(List<Account> accounts) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int current = 1;
        int size = accounts.size();
        while(current <= size) {
            for (Account curAcc : accounts) {
                int curId = curAcc.getId();
                if (curId == current) {
                    String name = curAcc.getName();
                    BigDecimal balance = curAcc.getBalance(this);
                    int typeId = curAcc.getType();
                    insert.insertAccount(name, balance, typeId);
                    current += 1;
                }
            }
        }
        insert.close();
    }

    private void insertUserAccountRelationship(List<int []> userAcc) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        for (int [] curArray : userAcc) {
            int userId = curArray[0];
            int accId = curArray[1];
            insert.insertUserAccount(userId, accId);
        }
        insert.close();
    }


    public void goBack(View view){
        super.onBackPressed();
    }
}
