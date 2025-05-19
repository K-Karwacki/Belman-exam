package dk.easv.belmanexam.auth;

import dk.easv.belmanexam.model.User;

public enum UserSession
{
  INSTANCE;
  private User loggedUser;

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
