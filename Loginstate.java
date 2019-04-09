import java.util.*;
import java.text.*;
import java.io.*;
public class Loginstate extends WarehouseState{
  private static final int CLERK_LOGIN = 0;
  private static final int USER_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int EXIT = 3;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private WareHouseContext context;
  private static Loginstate instance;
  private static Security secure;
  private Loginstate() {
      super();
  }

  public static Loginstate instance() {
    if (instance == null) {
      instance = new Loginstate();
    }
    return instance;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" ));
        if (value <= EXIT && value >= CLERK_LOGIN) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
 
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }

  private void clerk(){
    (WareHouseContext.instance()).setUser(WareHouseContext.IsClerk);
    (WareHouseContext.instance()).changeState(0);
  }
  private void manager(){
	    (WareHouseContext.instance()).setUser(WareHouseContext.IsManager);
	    (WareHouseContext.instance()).changeState(2);
	  }

  private void user(){
    String userID = getToken("Please input the user id: ");
    if (secure.validateClerk(userID)){
      (WareHouseContext.instance()).setUser(WareHouseContext.IsUser);
      (WareHouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id.");
  } 

  public void process() {
    int command;
    System.out.println("Please input 0 to login as Clerk\n"+ 
                        "input 1 to login as user\n" +
                        "input 2 to exit the system\n");     
    while ((command = getCommand()) != EXIT) {

      switch (command) {
        case CLERK_LOGIN:       clerk();
                                break;
        case USER_LOGIN:        user();
                                break;
        case MANAGER_LOGIN:     manager();
        break;
        default:                System.out.println("Invalid choice");
                                
      }
      System.out.println("Please input 0 to login as Clerk\n"+ 
                        "input 1 to login as user\n" +
                        "input 2 to login as manager\n"+
                        "input 3 to exit the system\n"); 
    }
    (WareHouseContext.instance()).changeState(3);
  }

  public void run() {
    process();
  }
}