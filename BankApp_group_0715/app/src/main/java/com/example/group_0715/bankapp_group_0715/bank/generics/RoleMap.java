package com.example.group_0715.bankapp_group_0715.bank.generics;

import android.content.Context;


import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;

public class RoleMap {

  private static EnumMap<Roles, Integer> RoleTypeMap = new EnumMap<Roles, Integer>(Roles.class);
  private static RoleMap instance = null;

  private RoleMap(Context context) {
    buildRolesEnumMap(context);    // private constructor, can only be called internally.
  }
  
  /**
   * Returns the instance of the class.
   * @return RoleMap.
   */
  public static RoleMap getInstance(Context context) {
    // if the instance of the class exists, return the existing instance
    // if not create an instance
    if (instance == null) {
      instance = new RoleMap(context);
      return instance;
    }
    return instance;
  }
  
  
  /**
   * Returns the EnumMap for RoleTypes.
   * @return EnumMap.
   */
  // return an EnumMap of Roles as keys, Integer as values.
  public static EnumMap<Roles, Integer> getmap() {
    return  RoleTypeMap;
  }
  
  /**
   * Private method to make an EnumMap for the Enum Roles through accessing the database.
   */
  private static void buildRolesEnumMap(Context context) {
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      // have a list of roleid's to loop through.
      List<Integer> roleIdList = select.getRoleList();
    select.close();
      for (Integer id : roleIdList) {
        // for each id, the Database helpers calls to compare the desired id's role.
        // and if the enum role matches with a role in DatabaseSelectHelper.
        String roleNmae = select.getRole(id);
        for (Roles role : Roles.values()) {
          if ((role + "").equalsIgnoreCase(roleNmae)) {
            // place the role and it's role id in the map if the Enum's class' roles
            // matches with the database
            RoleTypeMap.put(role, id);
          }
        }

      }

  }

  
  /**
   * Updates the current EnumMap for the class, through private methods.
   */
  public void updateRoleMap(Context context) {
    //update the EnumMap by clearing the private EnumMap variable, and reconnecting to database.
    RoleTypeMap.clear();
    buildRolesEnumMap(context);
  }
  
  /**
   * Returns the role id of the desired Role type.
   * @param typeName role type (in the Enum class Roles).
   * @return role type id based on type name
   */
  public int findRoleId(String typeName, Context context) {
    // checks what role type the parameter is requesting for
    // returns the id of that type through database helper methods.
    updateRoleMap(context);
    if (typeName.equalsIgnoreCase("ADMIN")) {
      return RoleTypeMap.get(Roles.ADMIN);
    } else if (typeName.equalsIgnoreCase("TELLER")) {
      return RoleTypeMap.get(Roles.TELLER);
    } else if (typeName.equalsIgnoreCase("CUSTOMER")) {
      return RoleTypeMap.get(Roles.CUSTOMER);
    }
    return -1;
  }
  

}
