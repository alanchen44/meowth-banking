package com.example.group_0715.bankapp_group_0715.bank.userfactory;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildAdmin;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildAdminImpl;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildCustomer;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildCustomerImpl;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildTeller;
import com.example.group_0715.bankapp_group_0715.bank.userbuilder.BuildTellerImpl;

public class UserFactory {
  /**
   * Build a User object given the specifications, without the authenticate status.
   * 
   * @param type Type of user being built
   * @param id id of the user
   * @param name name of the user
   * @param age age of the user
   * @param address address of the user
   * @return the User object with the given specifications
   */
  public User makeUserBuilderNoAuthenticate(String type, int id, String name, int age,
                                            String address, Context context) {
    // if user is ADMIN, use builder to create admin
    if (type.equalsIgnoreCase("ADMIN")) {
      BuildAdmin adminBuilder = new BuildAdminImpl(context);
      return adminBuilder.createAge(age).createAddress(address).createId(id).createName(name)
          .createRole(context).build();
      // if user is CUSTOMER, use builder to create customer
    } else if (type.equalsIgnoreCase("CUSTOMER")) {
      BuildCustomer customerBuilder = new BuildCustomerImpl(context);
      return customerBuilder.createAge(age).createAddress(address).createId(id).createName(name)
          .createRole(context).build();
      // if user is TELLER, use builder to create teller
    } else if (type.equalsIgnoreCase("TELLER")) {
      BuildTeller tellerBuilder = new BuildTellerImpl(context);
      return tellerBuilder.createAge(age).createAddress(address).createId(id).createRole(context)
          .createName(name).build();
    }
    return null;
  }

  /**
   * Build a User object given the specifications, including the authenticate status.
   * 
   * @param type Type of user being built
   * @param id id of the user
   * @param name name of the user
   * @param age age of the user
   * @param address address of the user
   * @param authenticate authentication status of the user
   * @return the User object with the given specifications
   */
  public User makeUserWithAuthenticate(String type, int id, String name, int age, String address,
      boolean authenticate, Context context) {
    // if user is ADMIN, use builder to create admin
    if (type.equalsIgnoreCase("ADMIN")) {
      BuildAdmin adminBuilder = new BuildAdminImpl(context);
      return adminBuilder.createAge(age).createAddress(address).createId(id).createName(name)
          .createAuth(authenticate).createRole(context).build();
      // if user is CUSTOMER, use builder to create customer
    } else if (type.equalsIgnoreCase("CUSTOMER")) {
      BuildCustomer customerBuilder = new BuildCustomerImpl(context);
      return customerBuilder.createAge(age).createAddress(address).createId(id).createName(name)
          .createAuth(authenticate).createRole(context).build();
      // if user is TELLER, use builder to create teller
    } else if (type.equalsIgnoreCase("TELLER")) {
      BuildTeller tellerBuilder = new BuildTellerImpl(context);
      return tellerBuilder.createAge(age).createAddress(address).createId(id).createName(name)
          .createRole(context).createAuth(authenticate).build();
    }
    return null;
  }

}
