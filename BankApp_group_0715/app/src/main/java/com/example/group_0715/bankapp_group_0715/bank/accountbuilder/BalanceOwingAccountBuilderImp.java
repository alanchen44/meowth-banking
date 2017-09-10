package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.BalanceOwingAccount;

import java.math.BigDecimal;



public class BalanceOwingAccountBuilderImp implements BalanceOwingAccountBuilder {
  private BigDecimal balance;
  private Integer id;
  private String name;
  private BalanceOwingAccount account;

  @Override
  public BalanceOwingAccountBuilder createBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }


  @Override
  public BalanceOwingAccountBuilder createId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public BalanceOwingAccountBuilder createName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public BalanceOwingAccount build(Context context) {
    // if all the necessary inputs are given, return the account
    if (this.id != null && this.name != null && this.balance != null && this.balance != null) {
      this.account = new BalanceOwingAccount(this.id, this.name, this.balance, context);
    }
    return this.account;
  }

}
