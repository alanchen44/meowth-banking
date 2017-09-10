package com.example.group_0715.bankapp_group_0715.bank.exceptions;

public class InsuffiecintFundsException extends Exception {

  /**
   * This is the serialVersionUID for the exception.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialize a InsuffiecintFundsException object with no error message.
   * 
   * @see Exception
   */
  public InsuffiecintFundsException() {
    super();
  }

  /**
   * Initialize a InsuffiecintFundsException object with an error message.
   * 
   * @param error the error message
   * @see Exception
   */
  public InsuffiecintFundsException(String error) {
    super(error);
  }

}
