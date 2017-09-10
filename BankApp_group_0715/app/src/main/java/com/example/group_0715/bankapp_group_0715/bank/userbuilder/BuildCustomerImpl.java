package com.example.group_0715.bankapp_group_0715.bank.userbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;

public class BuildCustomerImpl implements BuildCustomer {

  private Customer customer;
  
  /**
   * The constructor of this builder will create a new Customer object.
   */
  public BuildCustomerImpl(Context context) {
    // creates a new Customer object
    this.customer = new Customer();
    // sets the role for this Customer
    createRole(context);
  }
  
  @Override
  public BuildCustomer createId(int id) {
    customer.setId(id);
    return this;
  }

  @Override
  public BuildCustomer createAddress(String address) {
    customer.setAddress(address);
    return this;
  }

  @Override
  public BuildCustomer createAge(int age) {
    customer.setAge(age);
    return this;
  }

  @Override
  public BuildCustomer createName(String name) {
    customer.setName(name);
    return this;
  }


  @Override
  public BuildCustomer createRole(Context context) {
    customer.setRoleId("CUSTOMER", context);
    return this;
  }

  @Override
  public BuildCustomer createAuth(boolean authenticate) {
    customer.setAuthenticated(authenticate);
    return this;
  }

  @Override
  public Customer build() {
    return this.customer;
  }

}
