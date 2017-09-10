package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.RestrictedSavingsAccount;

import java.math.BigDecimal;


public class RestrictedSavingsAccountBuilderImp implements RestrictedSavingsAccountBuilder {
  private String name;
  private BigDecimal balance;
  private Integer id;
  private RestrictedSavingsAccount account;

  @Override
  public RestrictedSavingsAccountBuilder createBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  @Override
  public RestrictedSavingsAccountBuilder createId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public RestrictedSavingsAccountBuilder createName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public RestrictedSavingsAccount build(Context context) {
    // if all the necessary inputs are given, return the account
    if (this.id != null && this.name != null && this.balance != null) {
      this.account = new RestrictedSavingsAccount(this.id, this.name, this.balance, context);
    }
    return this.account;
  }

}
