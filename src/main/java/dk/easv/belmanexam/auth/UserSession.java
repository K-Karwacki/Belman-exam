package dk.easv.belmanexam.auth;

import dk.easv.belmanexam.entities.User;

public enum UserSession
{
  INSTANCE;
  private User loggedUser;

  UserSession(){
    loggedUser = null;
  }

  public void setLoggedUser(User loggedUser)
  {
    this.loggedUser = loggedUser;
  }

  public User getLoggedUser()
  {
    return loggedUser;
  }

  private void clearSession(){
    loggedUser = null;
  }
}
