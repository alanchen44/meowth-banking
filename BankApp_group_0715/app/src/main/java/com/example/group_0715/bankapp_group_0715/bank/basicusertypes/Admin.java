package com.example.group_0715.bankapp_group_0715.bank.basicusertypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Admin extends User {
	private static final long serialVersionUID = 9L;
  
  /**
   * Initialize default Admin constructor to begin using the builder.
   */
  public Admin() {
  }
  
  /**
   * Initialize a new Admin without authentication.
   * 
   * @param id the id of the admin
   * @param name the name of the admin
   * @param age the age of the admin
   * @param address the address of the admin
   * @throws SQLException 
   */
  public Admin(int id, String name, int age, String address, Context context) throws SQLException {
    // set variables in super and this class
    super.setId(id);
    super.setName(name);
    super.setAge(age);
    super.setAddress(address);
    super.setRoleString("ADMIN");
    super.setRoleId("ADMIN", context);
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    String password = select.getPassword(this.getId());
    select.close();
    super.setPassword(password);
  }

  /**
   * Initialize a new Admin with authentication.
   * 
   * @param id the id of the admin
   * @param name the name of the admin
   * @param age the age of the admin
   * @param address the address of the admin
   * @param authenticated authenticated status of the admin
   * @throws SQLException 
   */
  public Admin(int id, String name, int age, String address, boolean authenticated, Context context) throws SQLException {
    this(id, name, age, address, context);
    super.setAuthenticated(authenticated);
  }
  

  }
