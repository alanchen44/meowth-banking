package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacquelinechan on 2017-07-30.
 */

public class AdminPersonalMessageActivity extends AppCompatActivity {
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_view_personalmessage);

        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId", -1);
       // userId = getIntent().getExtras().getString("userId");

        //Load all admins onto scroll view
        List<User> filteredUsers = doAll();
        doFilter(filteredUsers, "", "", 0, Integer.MAX_VALUE);

    }

    public void goToAdminPersonalMessagePage(View view){
        Intent intent = new Intent(this, AdminViewPersonalMessageActivity.class);
        startActivity(intent);

    }


    public void goBack(View view) {
        super.onBackPressed();
    }


    private void doFilter(List<User> userList, String name, String address, int minAge, int maxAge) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.messagespersonaladminScroll);
        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        List<Integer> messagesid = new ArrayList<>();
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        messagesid = select.getAllMessagesList(userId);

        for (int message : messagesid) {
            //List<Integer> messagesid = new ArrayList<>();
            //DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            //messagesid = select.getAllMessagesList(cur.getId());

            //if (cur.getName().startsWith(name) && cur.getAddress().startsWith(address)
            //        && cur.getAge() >= minAge && cur.getAge() <= maxAge) {
                // Add Buttons
                final int id = message;
                //final String userName = cur.getName();
                //final String userAddress = cur.getAddress();
                //final int age = cur.getAge();

                Button b = new Button(this);
                b.setText("Message : " + id + " (Click for more info)");
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminPersonalMessageActivity.this, AdminViewPersonalMessageActivity.class);
                        intent.putExtra("id", id);
                        //intent.putExtra("name", userName);
                        //intent.putExtra("age", age);
                        //intent.putExtra("address", userAddress);
                        startActivity(intent);
                    }
                });
                linearLayout.addView(b);
            }
            scrollView.addView(linearLayout);

    }




//        List<Integer> messagesid = new ArrayList<>();
//        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
//        messagesid = select.getAllMessagesList(userId);
//
//        for (int mess: messagesid){
//            // Add Buttons
//            final int messid = mess;
//
//            Button b = new Button(this);
//            b.setText("Message : " + mess + " (Click for more info)");
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(AdminPersonalMessageActivity.this, AdminViewPersonalMessageActivity.class);
//                    intent.putExtra("id", messid);
////                    intent.putExtra("name", userName);
////                    intent.putExtra("age", age);
////                    intent.putExtra("address", userAddress);
//                    startActivity(intent);
//                }
//            });
//            linearLayout.addView(b);
//        }
//        select.close();
//
//
//        scrollView.addView(linearLayout);
//    }




//    private List<User> doAdmin() {
//        RoleMap rm = RoleMap.getInstance(this);
//        int adminRoleId = rm.findRoleId("ADMIN", this);
//        List<User> userList = new ArrayList<>();
//        boolean hasNext = true;
//        int current = 1;
//        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
//        while (hasNext) {
//            User user = select.getUserDetailsInfo(current, this);
//            if (user != null) {
//                if (user.getRoleId() == adminRoleId) {
//                    userList.add(user);
//                }
//            } else {
//                hasNext = false;
//            }
//            current += 1;
//        }
//        select.close();
//        return userList;
//    }


    private List<User> doAll() {
        List<User> userList = new ArrayList<>();
        boolean hasNext = true;
        int current = 1;
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        while (hasNext) {
            User user = select.getUserDetailsInfo(current, this);
            if (user != null) {
                userList.add(user);
            } else {
                hasNext = false;
            }
            current += 1;
        }
        select.close();
        return userList;
    }







}

