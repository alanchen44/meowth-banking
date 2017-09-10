package com.example.group_0715.bankapp_group_0715.bank.userbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;

public interface BuildCustomer {
  
  /**
   * Creates id for customer.
   * @param id the id for customer 
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createId(int id);
  
  /**
   * Creates customer address.
   * @param address the customer address
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createAddress(String address);
  
  /**
   * Creates customer age.
   * @param age the customer's age
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createAge(int age);
  
  /**
   * Creates customer name.
   * @param name the name of the customer
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createName(String name);
  
  /**
   * Creates customer role.
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createRole(Context context);
  
  /**
   * Creates customer authentication status.
   * @param authenticate the customer authentication status
   * @return the builder class that is constructing this customer
   */
  public BuildCustomer createAuth(boolean authenticate);
  
  /**
   * Get the customer object with its current specifications.
   * @return the customer object with its current specifications
   */
  public Customer build();
}
