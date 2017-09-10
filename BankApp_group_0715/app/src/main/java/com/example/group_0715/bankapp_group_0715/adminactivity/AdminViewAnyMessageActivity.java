package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.filteractivity.FilterMessageActivity;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


/**
 * Created by Alan on 2017-08-20.
 */

public class AdminViewAnyMessageActivity extends AppCompatActivity {

    private boolean hideViewed;
    // the list that will contain the message IDs that currently matches filter results
    // this list has all the IDs of the filter results regardless of viewed status
    private List<Integer> currentList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_view_any_message);

        hideViewed = false;
        Button hideViewedButton = (Button) findViewById(R.id.displayViewedButton);
        hideViewedButton.setText(R.string.hideViewed);

        // display all messages to screen
        List<Integer> allMessageIds = getAllMessages();
        doFilter(allMessageIds, new String[0], 0, 0);
        displayResultsOnScreen();
    }

    public void performResetFilterMessage(View view) {
        doFilter(getAllMessages(), new String[0], 0, 0);
        displayResultsOnScreen();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    public void goToFilterMessagePage(View view) {
        Intent intent = new Intent(this, FilterMessageActivity.class);
        startActivityForResult(intent, 0);
    }

    public void performDisplayViewed(View view) {
        Button hideViewedButton = (Button) findViewById(R.id.displayViewedButton);
        if (hideViewed) {
            hideViewed = false;
            hideViewedButton.setText(R.string.hideViewed);
        } else {
            hideViewed = true;
            hideViewedButton.setText(R.string.showViewed);
        }

        displayResultsOnScreen();
    }

    private void displayResultsOnScreen() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.messageScroll);
        scrollView.removeAllViews();
        LinearLayout layoutScroll = new LinearLayout(this);
        layoutScroll.setOrientation(LinearLayout.VERTICAL);

        int numOfResults = 0;
        // add only Not-Viewed messages to the scrollview from the currentList
        if (hideViewed) {
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            for (int curId : currentList){
                int viewedStatus = select.getMessageViewed(curId);
                if (viewedStatus == 0) {
                    numOfResults += 1;
                    int recipient = select.getMessageRecipient(curId);

                    Button b = new Button(this);
                    b.setText("ID: " + curId + ",     Recipient ID: " + recipient);
                    // we need final to be able to use this variable in the inner class
                    final int msgId = curId;
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setButtonDialog(msgId);
                        }
                    });

                    layoutScroll.addView(b);
                }
            }
            select.close();

        } else {
            // add all messages from currentList to scrollview
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            numOfResults = currentList.size();
            for (int curId : currentList) {
                int recipient = select.getMessageRecipient(curId);
                Button b = new Button(this);
                b.setText("ID: " + curId + ",     Recipient ID: " + recipient);
                // we need final to be able to use this variable in the inner class
                final int msgId = curId;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonDialog(msgId);
                    }
                });
                layoutScroll.addView(b);
            }
            select.close();
        }

        // display numOfResults to screen
        displayNumOfResults(numOfResults);

        // display msg buttons
        scrollView.addView(layoutScroll);

    }

    private void setButtonDialog(int msgId) {
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        String message = select.getSpecificMessage(msgId);

        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setMessage(message);
        msgBox.setTitle(R.string.message);
        msgBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        msgBox.setCancelable(false);
        msgBox.create().show();
    }

    private void displayNumOfResults(int numOfResults) {
        // display numOfResults to screen
        TextView numOfResultsTextView = (TextView) findViewById(R.id.findNumMsg);
        numOfResultsTextView.setText("" + numOfResults);

        // change text to singular form if only 1 account is displayed, plural otherwise
        TextView accDisplayedField = (TextView)findViewById(R.id.msgDisplayed);
        if(numOfResults == 1) {
            accDisplayedField.setText(R.string.messageDisplayed);
        } else {
            accDisplayedField.setText(R.string.messagesDisplayed);
        }
    }

    private List<Integer> getAllMessages() {
        List<Integer> allMessages = new ArrayList<>();

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> userIdList = select.getUsersDetailsList(this);
        for (User curUser : userIdList) {
            int curUserId = curUser.getId();
            List<Integer> curUserMsgList = select.getAllMessagesList(curUserId);
            for (int msgId : curUserMsgList) {
                allMessages.add(msgId);
            }
        }
        select.close();
        // sorts allMessages list in ascending order of IDs
        Collections.sort(allMessages);
        return allMessages;
    }

    private void doFilter(List<Integer> msgIdList, String[] recipients, int minChar, int maxChar) {
        // filter and set currentList to these filtered results
        currentList = new ArrayList<Integer>();
        if (recipients.length > 0) {
            for (int curMsgId : msgIdList) {
                for (String curUserIdString : recipients) {
                    int curUserIdInt = Integer.parseInt(curUserIdString);
                    DatabaseSelectHelper select = new DatabaseSelectHelper(this);
                    int curMsgRecipient = select.getMessageRecipient(curMsgId);
                    if (curUserIdInt == curMsgRecipient) {
                        String message = select.getSpecificMessage(curMsgId);
                        select.close();
                        int msgLen = message.length();
                        if (!currentList.contains(curMsgId) && msgLen >= minChar && msgLen <= maxChar) {
                            currentList.add(curMsgId);
                        }
                    }
                    select.close();
                }
            }
        } else {
            currentList = msgIdList;
        }
    }

    private void findMsgById(String msgIdString) {
        currentList = new ArrayList<Integer>();
        try {
            int msgId = Integer.parseInt(msgIdString);
            currentList.add(msgId);
        } catch (NumberFormatException e) {
            // do nothing
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String operation = data.getStringExtra("operation");
                // if the find button was pressed from the filter activity
                if (operation.equals("find_by_id")) {
                    String msgIdString = data.getStringExtra("id");
                    //set currentList based on the given input
                    findMsgById(msgIdString);
                    //display results which will decide whether to show viewed msg or not
                    displayResultsOnScreen();

                }


                if (operation.equals("filter")) {
                    String [] recipients = data.getStringArrayExtra("recipients");
                    int minChar = data.getIntExtra("minChar", 0);
                    int maxChar = data.getIntExtra("maxChar", Integer.MAX_VALUE);

                    List<Integer> allMessageIDs = getAllMessages();

                    // set currentList based on filtered options
                    doFilter(allMessageIDs, recipients, minChar, maxChar);

                    //display results which will decide whether to show viewed msg or not
                    displayResultsOnScreen();
                }
            }
        }
    }




}
