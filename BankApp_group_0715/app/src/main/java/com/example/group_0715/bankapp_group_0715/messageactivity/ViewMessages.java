package com.example.group_0715.bankapp_group_0715.messageactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;

import java.util.List;

/**
 * Created by Alan on 2017-08-18.
 */

public class ViewMessages extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_list_activity);

        Intent intent = this.getIntent();
        int userId = intent.getIntExtra("userId", -1);

        if(userId == -1) {
            showErrorBox();
        } else {
            ScrollView msgScroll = (ScrollView) findViewById(R.id.messageScroll);
            msgScroll.removeAllViews();
            LinearLayout scrollContents = new LinearLayout(this);
            scrollContents.setOrientation(LinearLayout.VERTICAL);

            int count = 0;
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            List<Integer> allMsgId = select.getAllMessagesList(userId);

            // iterate through the list of user messages and display the unread messages
            for (int curId : allMsgId) {
                // retrieve message and view status from database
                int msgStatus = select.getMessageViewed(curId);
                String msgString = select.getSpecificMessage(curId);
                // if the message is not viewed, add to LinearLayout for display
                if(msgStatus == 0) {
                    TextView msgTxt = new TextView(this);
                    msgTxt.setText(msgString);
                    scrollContents.addView(msgTxt);
                    count += 1;

                    // mark the message as "VIEWED"
                    DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
                    update.updateUserMessageState(curId);
                    update.close();
                }
            }
            select.close();

            // display number of new messages displayed
            TextView numOfMsg = (TextView) findViewById(R.id.findNumMsg);
            displayNumOfMsg(count);

            msgScroll.addView(scrollContents);
        }

    }

    private void displayNumOfMsg(int num) {
        TextView totalMsgField = (TextView) findViewById(R.id.findNumMsg);
        totalMsgField.setText(num + "");
        // change text to singular form if only 1 msg is displayed, plural otherwise
        TextView accDisplayedField = (TextView)findViewById(R.id.msgDisplayed);
        if(num == 1) {
            accDisplayedField.setText(R.string.msgDisplayed);
        } else {
            accDisplayedField.setText(R.string.msgsDisplayed);
        }
    }

    private void showErrorBox() {
        AlertDialog.Builder errorBox = new AlertDialog.Builder(this);
        errorBox.setMessage(R.string.messageError);
        errorBox.setTitle(R.string.error);
        errorBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        errorBox.create().show();
    }

    public void goBack(View view) {
        runDialog();
    }

    @Override
    public void onBackPressed() {
        runDialog();
    }

    private void runDialog() {
        AlertDialog.Builder confirmBox = new AlertDialog.Builder(this);
        confirmBox.setMessage(R.string.msgLeaveTxt);
        confirmBox.setTitle(R.string.confirm);
        confirmBox.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        confirmBox.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        confirmBox.setCancelable(false);
        confirmBox.create().show();
    }

}
