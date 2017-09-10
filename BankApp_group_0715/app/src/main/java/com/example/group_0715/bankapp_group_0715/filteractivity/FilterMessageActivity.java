package com.example.group_0715.bankapp_group_0715.filteractivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.canRetrieveWindowContent;
import static android.R.attr.name;
import static com.example.group_0715.bankapp_group_0715.R.id.userId;
import static com.example.group_0715.bankapp_group_0715.R.id.userIdText;
import static com.example.group_0715.bankapp_group_0715.R.id.userPassword;
import static com.example.group_0715.bankapp_group_0715.R.string.maxAge;
import static com.example.group_0715.bankapp_group_0715.R.string.minAge;

/**
 * Created by Alan on 2017-08-20.
 */

public class FilterMessageActivity extends AppCompatActivity {

    private final String EXPRESSION = "^[ ]*[0-9]+[ ]*([ ]*,[ ]*[0-9]+[ ]*)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_message_activity);

    }

    public void performFilterMessage(View view) {
        EditText recipientField = (EditText)findViewById(R.id.filterRecipentsText);
        String recipientString = recipientField.getText().toString();
        EditText minCharField = (EditText) findViewById(R.id.minCharText);
        String minCharString = minCharField.getText().toString();
        EditText maxCharField = (EditText) findViewById(R.id.maxCharText);
        String maxCharString = maxCharField.getText().toString();

        boolean canFilter = true;

        String [] userIdStringArray = createIdArray(recipientString);
        if (userIdStringArray == null) {
            canFilter = false;
        } else {
            for (String curId : userIdStringArray) {
                boolean validId = validIdCheck(curId);
                if(!validId) {
                    canFilter = false;
                }
            }
        }

        int minChar = 0;
        if(minCharString.equals("")) {
            minChar = 0;
        }
        else {
            try {
                minChar = Integer.parseInt(minCharString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        int maxChar = Integer.MAX_VALUE;
        if(maxCharString.equals("")){
            maxChar = Integer.MAX_VALUE;
        } else {
            try {
                maxChar = Integer.parseInt(maxCharString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        if(minChar > maxChar) canFilter = false;

        if(!canFilter) {
            AlertDialog.Builder errorBox = new AlertDialog.Builder(this);
            errorBox.setMessage("Cannot perform filter. Please check input.");
            errorBox.setTitle("Error");
            errorBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            errorBox.setCancelable(false);
            errorBox.create().show();
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("operation", "filter");
            resultIntent.putExtra("recipients", userIdStringArray);
            resultIntent.putExtra("minChar", minChar);
            resultIntent.putExtra("maxChar", maxChar);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    public void performFindMessage(View view) {
        EditText userIdField = (EditText) findViewById(R.id.idText);
        String userIdText = userIdField.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("operation", "find_by_id");
        resultIntent.putExtra("id", userIdText);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void goBack(View view) {
        super.onBackPressed();
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

}
