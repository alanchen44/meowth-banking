package com.example.group_0715.bankapp_group_0715.bank.bank;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class SerializeObject {
  //declaring two lists for storing accoutn and user
  public static List<Account> accountList = new ArrayList<Account>();
  public static List<User> userList = new ArrayList<User>();
  
  /**
   * create a new file with name "database_copy.ser",
   * iff the file does not exist
   */
  private static void createOrCheckFile(){
    File file = new File("database_copy.ser");
    if(!(file.exists())){
      try {
	    file.createNewFile();
	  }catch (IOException e) {
	    // TODO Auto-generated catch block
		e.printStackTrace();
	  }
    }
  }
  
  /**
   * adding this user object to user list, and
   * ovveride file with new user list.
   * @param user user that will be addded to list
   */
  public static void writeObjectToFile(User user){
	//if file does not exist, create a new file
    createOrCheckFile();
    //add user to user list
    userList.add(user);
  }
  
  /**
   * adding account to account list
   * @param account account that will be added to list
   */
  public static void writeObjectToFile(Account account){
    createOrCheckFile();
    //add account to account list
    accountList.add(account);
  }
  
  /**
   * ovveride entire files.
   */
  public static boolean overrideFile(){
	//create "database_copy.ser" iff file does not exist.
    createOrCheckFile();
    //Empty current file
    try{
      PrintWriter writer = new PrintWriter("database_copy.ser");
      writer.close();
    }catch(FileNotFoundException e){
      e.printStackTrace();	
    }
    //write list to file
    try{
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("database_copy.ser"));
      os.writeObject(accountList);
      os.writeObject(userList);
      os.close();
      return true;
    }catch(IOException e){
      e.printStackTrace();
      return false;
    }

  }
  
  /**
   * update userList or accountList, and override files,
   * iff database has been updated for a given object.
   * @param id id for updated account or user
   * @param obj object that will be updated to database
   */
  public static void updateList(int id, Object obj){
	//if this object is user
    if(obj instanceof User){
      //remove old user from user list
      userList.remove(id - 1);
      //add new user from database, based on id
      userList.add(id -1, (User) obj); 
    }
    //if obj is account
    if(obj instanceof Account){
      //remove old account object
      accountList.remove(id - 1);
      //add new account form database, based on id
      accountList.add(id - 1, (Account) obj);
    }
    //ovveride entire files
    overrideFile();
  }
}
