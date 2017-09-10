package com.example.group_0715.bankapp_group_0715.bank.accountfactory;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.BalanceOwingAccountBuilder;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.BalanceOwingAccountBuilderImp;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.ChequingAccountBuilder;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.ChequingAccountBuilderImp;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.RestrictedSavingsAccountBuilder;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.RestrictedSavingsAccountBuilderImp;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.SavingsAccountBuilder;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.SavingsAccountBuilderImp;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.TFSABuilder;
import com.example.group_0715.bankapp_group_0715.bank.accountbuilder.TFSABuilderImp;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.math.BigDecimal;


public class AccountFactory {
  /**
   * Makes an account with the given input.
   * 
   * @param type the type of account. Must be RESTRICTEDSAVING, SAVING, CHEQUING, BALANCEOWING, or
   *        TFSA
   * @param id the id of the account
   * @param name the name of the account
   * @param balance the balance of the account
   * @return the account created by these specifications
   */
  public Account makeAccount(String type, int id, String name, BigDecimal balance, Context context) {
    // if SAVING, create a savings account
    if (type.equalsIgnoreCase("SAVING")) {
      SavingsAccountBuilder builder = new SavingsAccountBuilderImp();
      builder.createBalance(balance);
      builder.createId(id);
      builder.createName(name);
      return builder.build(context);
      // if CHEQUING, create a chequing account
    } else if (type.equalsIgnoreCase("CHEQUING")) {
      ChequingAccountBuilder builder = new ChequingAccountBuilderImp();
      builder.createBalance(balance);
      builder.createId(id);
      builder.createName(name);
      return builder.build(context);
      // if TFSA, create a TFSA
    } else if (type.equalsIgnoreCase("TFSA")) {
      TFSABuilder builder = new TFSABuilderImp();
      builder.createBalance(balance);
      builder.createId(id);
      builder.createName(name);
      return builder.build(context);
    } else if (type.equalsIgnoreCase("RESTRICTEDSAVING")) {
      RestrictedSavingsAccountBuilder builder = new RestrictedSavingsAccountBuilderImp();
      builder.createBalance(balance);
      builder.createId(id);
      builder.createName(name);
      return builder.build(context);
    } else if (type.equalsIgnoreCase("BALANCEOWING")) {
      BalanceOwingAccountBuilder builder = new BalanceOwingAccountBuilderImp();
      builder.createBalance(balance);
      builder.createId(id);
      builder.createName(name);
      return builder.build(context);
    }
    return null;
  }
}
