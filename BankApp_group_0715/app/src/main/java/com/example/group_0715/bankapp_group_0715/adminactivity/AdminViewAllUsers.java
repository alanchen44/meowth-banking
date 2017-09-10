package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.filteractivity.FilterUserActivity;
import com.example.group_0715.bankapp_group_0715.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2017-07-29.
 */

public class AdminViewAllUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_view_all_users_activity);

        //Load all admins onto scroll view
        List<User> filteredUsers = doAll();
        doFilter(filteredUsers, "", "", 0, Integer.MAX_VALUE);

    }

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

    private List<User> doAdmin() {
        RoleMap rm = RoleMap.getInstance(this);
        int adminRoleId = rm.findRoleId("ADMIN", this);
        List<User> userList = new ArrayList<>();
        boolean hasNext = true;
        int current = 1;
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        while (hasNext) {
            User user = select.getUserDetailsInfo(current, this);
            if (user != null) {
                if (user.getRoleId() == adminRoleId) {
                    userList.add(user);
                }
            } else {
                hasNext = false;
            }
            current += 1;
        }
        select.close();
        return userList;
    }

    private List<User> doTeller() {
        RoleMap rm = RoleMap.getInstance(this);
        int tellerRoleId = rm.findRoleId("TELLER", this);
        List<User> userList = new ArrayList<>();
        boolean hasNext = true;
        int current = 1;
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        while (hasNext) {
            User user = select.getUserDetailsInfo(current, this);
            if (user != null) {
                if (user.getRoleId() == tellerRoleId) {
                    userList.add(user);
                }
            } else {
                hasNext = false;
            }
            current += 1;
        }
        select.close();
        return userList;
    }

    private List<User> doCustomer() {
        RoleMap rm = RoleMap.getInstance(this);
        int customerRoleId = rm.findRoleId("CUSTOMER", this);
        List<User> userList = new ArrayList<>();
        boolean hasNext = true;
        int current = 1;
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        while (hasNext) {
            User user = select.getUserDetailsInfo(current, this);
            if (user != null) {
                if (user.getRoleId() == customerRoleId) {
                    userList.add(user);
                }
            } else {
                hasNext = false;
            }
            current += 1;
        }
        select.close();
        return userList;
    }

    private void findUserById(String userIdString) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.adminScroll);
        scrollView.removeAllViews();
        try {
            int userId = Integer.parseInt(userIdString);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            User user = select.getUserDetailsInfo(userId, this);
            if (user != null) {
                // Add Buttons
                final int id = user.getId();
                final String userName = user.getName();
                final String address = user.getAddress();
                final int age = user.getAge();

                Button b = new Button(this);
                b.setText("ID: " + id + ",     " + "NAME: " + userName);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminViewAllUsers.this, AdminViewUserDetailsActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", userName);
                        intent.putExtra("age", age);
                        intent.putExtra("address", address);
                        startActivity(intent);
                    }
                });
                linearLayout.addView(b);
            }
            select.close();
            scrollView.addView(linearLayout);
        } catch (NumberFormatException e) {
        }
    }

    private void doFilter(List<User> userList, String name, String address, int minAge, int maxAge) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.adminScroll);
        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (User cur : userList) {
            if (cur.getName().startsWith(name) && cur.getAddress().startsWith(address)
                    && cur.getAge() >= minAge && cur.getAge() <= maxAge) {
                // Add Buttons
                final int id = cur.getId();
                final String userName = cur.getName();
                final String userAddress = cur.getAddress();
                final int age = cur.getAge();
                Button b = new Button(this);
                b.setText("ID: " + id + ",     " + "NAME: " + userName);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminViewAllUsers.this, AdminViewUserDetailsActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", userName);
                        intent.putExtra("age", age);
                        intent.putExtra("address", userAddress);
                        startActivity(intent);
                    }
                });
                linearLayout.addView(b);
            }
        }
        scrollView.addView(linearLayout);
    }


    public void goToFilterUserPage(View view) {
        Intent intent = new Intent(this, FilterUserActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String operation = data.getStringExtra("operation");
                // if the find button was pressed from the filter activity
                if (operation.equals("find_by_id")) {
                    String userId = data.getStringExtra("id");
                    findUserById(userId);
                }

                if (operation.equals("filter")) {
                    String userType = data.getStringExtra("userType");
                    String name = data.getStringExtra("name");
                    String address = data.getStringExtra("address");
                    int minAge = data.getIntExtra("minAge", 0);
                    int maxAge = data.getIntExtra("maxAge", Integer.MAX_VALUE);

                    List<User> filteredUsers = new ArrayList<>();
                    if (userType.equals("All")) {
                        filteredUsers = doAll();
                    } else if (userType.equals("Admin")) {
                        filteredUsers = doAdmin();
                    } else if (userType.equals("Teller")) {
                        filteredUsers = doTeller();
                    } else if (userType.equals("Customer")) {
                        filteredUsers = doCustomer();
                    }
                    doFilter(filteredUsers, name, address, minAge, maxAge);
                }
            }
        }
    }

    public void performResetFilter(View view) {
        List<User> filteredUsers = doAll();
        doFilter(filteredUsers, "", "", 0, Integer.MAX_VALUE);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
