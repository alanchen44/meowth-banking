package com.example.group_0715.bankapp_group_0715.bank.basicusertypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.bank.security.PasswordHelpers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable{

  // Declaring local fields
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private String roleString;
  private boolean authenticated;
  private static final long serialVersionUID = 6L;
  private String password;

  /**
   * Return the id of the User.
   * @return id of the User
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the Id of the User.
   * @param id new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Return the name of the User.
   * @return name of the User
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the User.
   * @param name new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Return the age of the User.
   * @return age of the User
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the age of the User.
   * @param age new age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Return the address of the User.
   * @return address of the User
   */
  public String getAddress() {
    return address;
  }

  /**
    * Sets the address of the User.
   * @param address new address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Set the role id of the User.
   */
  public void setRoleId(String roleName, Context context) {
    this.roleId = RoleMap.getInstance(context).findRoleId(roleName, context);
  }
  
  /**
   * Return the role id of the User.
   * @return role id of the User
   */
  public int getRoleId() {
    return this.roleId;
  }

  public void setRoleString(String role) {
    this.roleString = role;
  }

  public String getRoleString(){
    return this.roleString;
  }
  /**
   * Return authentication status.
   * @return authentication status of the user
   */
  public boolean getAuthenticated() {
    return this.authenticated;
  }
  
  /**
   * Set authentication status of this user.
   * @param authenticated status
   */
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
  
  /**
   * Authenticated the user with the given password.
   * @param password the password
   * @return true if authenticated, false otherwise
   */
  public final boolean authenticate(String password, Context context) {
    String databasePassword;

    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    databasePassword = select.getPassword(this.id);
    select.close();

    boolean auth = PasswordHelpers.comparePassword(databasePassword, password);
    this.authenticated = auth;
    return auth;
  }

  /**
   * Get list of all messages id.
   * @param context the context of the appplication
   * @return the list of all messages id
   */
  public List<Integer> viewAllMessagesID(Context context){
    List<Integer> messages = new ArrayList<Integer>();
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    messages = select.getAllMessagesList(this.id);
    select.close();
    return messages;
  }

  /**
   * View specific message.
   * @param messageId id of the message
   * @param context the application context
   * @return the contents of the message
   */
  public String viewSpecificMessage(int messageId, Context context) {
    String message = "";

    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    message = select.getSpecificMessage(messageId);
    select.close();

    DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
    update.updateUserMessageState(messageId);
    update.close();
    return message;
      
  }
  
  public void setPassword(String password){
    this.password = password;
  }
  
  public String getPassword(){
    return this.password;
  }
  
}
