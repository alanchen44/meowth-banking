package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.SavingsAccount;

import java.math.BigDecimal;


public class SavingsAccountBuilderImp implements SavingsAccountBuilder {
  private String name;
  private BigDecimal balance;
  private Integer id;
  private SavingsAccount account;

  @Override
  public SavingsAccountBuilder createBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }


  @Override
  public SavingsAccountBuilder createId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public SavingsAccountBuilder createName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public SavingsAccount build(Context context) {
    // if all the necessary inputs are given, return the account
    if (this.id != null && this.name != null && this.balance != null && this.balance != null) {
      this.account = new SavingsAccount(this.id, this.name, this.balance, context);
    }
    return this.account;
  }

}
