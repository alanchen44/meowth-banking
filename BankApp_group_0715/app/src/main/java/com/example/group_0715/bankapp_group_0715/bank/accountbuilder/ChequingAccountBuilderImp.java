package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.ChequingAccount;

import java.math.BigDecimal;


public class ChequingAccountBuilderImp implements ChequingAccountBuilder {
  private BigDecimal balance;
  private Integer id;
  private String name;
  private ChequingAccount account;

  @Override
  public ChequingAccountBuilder createBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }


  @Override
  public ChequingAccountBuilder createId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public ChequingAccountBuilder createName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public ChequingAccount build(Context context) {
    // if all the necessary inputs are given, return the account
    if (this.id != null && this.name != null && this.balance != null && this.balance != null) {
      this.account = new ChequingAccount(this.id, this.name, this.balance, context);
    }
    return this.account;
  }

}
