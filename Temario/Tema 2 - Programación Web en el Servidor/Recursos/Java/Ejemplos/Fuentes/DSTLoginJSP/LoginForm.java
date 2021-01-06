import java.io.*;

public class LoginForm implements Serializable {
  private String loginName;

  public LoginForm() {
  loginName = "";
  }

  public String getLoginName() {
  return loginName;
  }

  public void setLoginName(String loginName) {
  this.loginName = loginName.trim();
  }

}