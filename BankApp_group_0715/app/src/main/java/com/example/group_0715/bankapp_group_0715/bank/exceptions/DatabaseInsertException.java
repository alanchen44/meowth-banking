package com.example.group_0715.bankapp_group_0715.bank.exceptions;

public class DatabaseInsertException extends Exception {

  /**
   * This is the serialVersionUID for the exception.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialize a DatabaseInsertException object with no error message.
   * 
   * @see Exception
   */
  public DatabaseInsertException() {
    super();
  }

  /**
   * Initialize a DatabaseInsertException object with an error message.
   * 
   * @param error the error message
   * @see Exception
   */
  public DatabaseInsertException(String error) {
    super(error);
  }

}
