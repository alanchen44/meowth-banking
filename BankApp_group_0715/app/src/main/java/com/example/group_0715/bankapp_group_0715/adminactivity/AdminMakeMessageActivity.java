package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Alan on 2017-08-19.
 */

public class AdminMakeMessageActivity extends AppCompatActivity {

    private final String EXPRESSION = "^[ ]*[0-9]+[ ]*([ ]*,[ ]*[0-9]+[ ]*)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_make_message);

    }

    private String [] createIdArray(String idText) {
        String [] idArray;
        // compile the regex to get the pattern
        Pattern pattern = Pattern.compile(EXPRESSION);
        // match this pattern to the input text
        Matcher matcher = pattern.matcher(idText);

        if (matcher.matches()) {
            String removeSpaces = idText.replaceAll(" ", "");
            idArray = removeSpaces.split(",");
        } else {
            idArray = null;
        }

        return idArray;
    }


    private boolean validIdCheck(String idText) {
        boolean validId = false;
        int idInt = -1;
        // verify userId could be found on db
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> allUsers = select.getUsersDetailsList(this);
        select.close();
        try {
            idInt = Integer.parseInt(idText);

            for (User cur : allUsers) {
                int curId = cur.getId();
                if (curId == idInt) {
                    validId = true;
                }
            }
        } catch (NumberFormatException e) {
            validId = false;
        }
        return validId;
    }


    public void makeMessage(View view){
        EditText userId = (EditText) findViewById(R.id.idPrompt);
        EditText userMessage = (EditText) findViewById(R.id.messagePrompt);

        String idText = userId.getText().toString();
        String messageText = userMessage.getText().toString();

        int idInt = -1;
        boolean messageAdded = false;
        boolean messageTooLong = false;
        boolean validId = true;

        // verify message length
        if (messageText.length() <= 512) {
            messageTooLong = false;
        } else {
            messageTooLong = true;
        }

        String [] idStringArray= createIdArray(idText);

        // verify all IDs in the array
        for (String cur : idStringArray) {
            boolean isValid = validIdCheck(cur);
            if(!isValid) {
                validId = false;
            }
        }

        // add only if message not over 512 characters and userId is valid customer
        if (!messageTooLong && validId) {
            DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
            for(String curString : idStringArray) {
                idInt = Integer.parseInt(curString);
                long messageId = insert.insertMessage(idInt, messageText);
            }
            insert.close();
            messageAdded = true;
        } else {
            messageAdded = false;
        }

        String printMessage = "";
        String successString = "";
        if (messageTooLong) {
            successString = getResources().getString(R.string.error);
            printMessage = getResources().getString(R.string.errorMessageTooLong);
        } else if (!validId) {
            successString = getResources().getString(R.string.error);
            printMessage = getResources().getString(R.string.invalidIdGeneral);
        } else if (messageAdded && validId && !messageTooLong) {
            printMessage = getResources().getString(R.string.successMakeMessage);
            successString = getResources().getString(R.string.successTitle);
        } else {
            successString = getResources().getString(R.string.error);
            printMessage = getResources().getString(R.string.unableToCreateMessage);
        }

        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        successBox.setMessage(printMessage);
        successBox.setTitle(successString);
        successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        successBox.setCancelable(false);
        successBox.create().show();

    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
