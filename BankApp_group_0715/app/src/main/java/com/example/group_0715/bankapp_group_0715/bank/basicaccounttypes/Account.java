package com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Account implements Serializable{

  // Declaring local fields
  private int id;
  private String name;
  private BigDecimal balance;
  private int type;
  private static final long serialVersionUID = 2L;

  /**
   * Returns the id of the Account.
   * 
   * @return the id of account
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id of the Account.
   * 
   * @param id new id for account
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the name of the Account.
   * 
   * @return name of the account
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the Account.
   * 
   * @param name new name of the Account
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the balance of the Account.
   * 
   * @return balance of the Account
   */
  public BigDecimal getBalance(Context context) {

    return this.balance;
  }

  /**
   * Sets the balance of the Account.
   * 
   * @param balance new balance of the Account
   */
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  /**
   * Returns the type of the Account.
   * 
   * @return type of the Account
   */
  public int getType() {
    return type;
  }

  /**
   * Set the type of the account.
   * @param typeName the new type name
   */
  public void setType(String typeName, Context context) {
    this.type = AccountTypesMap.getInstance(context).getAccountTypeId(typeName, context);
  }
}
