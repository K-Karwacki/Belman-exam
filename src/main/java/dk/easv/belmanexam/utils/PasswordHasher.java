package dk.easv.belmanexam.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher
{
  public static String hashPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }
}
