package models;

/**
 *
 * @author Dario
 */
public class AuthenticationFailedException extends Exception {
  public AuthenticationFailedException() {
  }

  public AuthenticationFailedException(String msg) {
    super(msg);
  }
}
