package com.example.group_0715.bankapp_group_0715.bank.basicusertypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.sql.SQLException;



public class Teller extends User {
	private static final long serialVersionUID = 7L;

  /**
   * Initialize default Teller constructor to begin using the builder.
   */
  public Teller() {
  }
  
  /**
   * Initialize a Teller without authentication.
   * 
   * @param id the id of the teller
   * @param name the name of the teller
   * @param age the age of the teller
   * @param address the address of the teller
   * @throws SQLException 
   */
  public Teller(int id, String name, int age, String address, Context context) throws SQLException {
    super.setId(id);
    super.setName(name);
    super.setAge(age);
    super.setAddress(address);
    super.setRoleId("TELLER", context);
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    String password = select.getPassword(this.getId());
    select.close();
    super.setPassword(password);
  }

  /**
   * Initialize a Teller with authentication.
   * 
   * @param id the id of the teller
   * @param name the name of the teller
   * @param age the age of the teller
   * @param address the address of the teller
   * @param authenticated if user is authenticated
   * @throws SQLException 
   */
  public Teller(int id, String name, int age, String address, boolean authenticated, Context context) throws SQLException {
    this(id, name, age, address, context);
    super.setAuthenticated(authenticated);
  }

}
