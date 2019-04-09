// Ivy - Manager

import java.util.*;

import javax.swing.LookAndFeel;

import java.text.*;
import java.io.*;
public class Managerstate extends WarehouseState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static Managerstate instance;
  private static final int EXIT = 0;
  private static final int MODIFY_PRICE = 1;
  private static final int ASSIGN_PRODUCT = 2; 
  private static final int ADD_MANUFACTURER = 3;
  private static final int CLERKMENU = 4;
  private static final int HELP = 5;
  private Managerstate() {
      super();
      warehouse = Warehouse.instance();
      context = WarehouseContext.instance();
  }

  public static Managerstate instance() {
    if (instance == null) {
      instance = new Managerstate();
    }
    return instance;
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
  
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Enter the number: ");
      }
    } while (true);
  }
  
  public double getFloat(String prompt)
  {
      do {
            try
            {
                String item = getToken(prompt);
                Double decimal = Double.valueOf(item);
                return decimal.doubleValue();
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("Enter the number: ");
            }
      } while (true);
  }
  
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Enter the date (mm/dd/yy format) ");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP ));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter the number: ");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter the number to select the menu.\n");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(MODIFY_PRICE + " to modify price");
    System.out.println(ASSIGN_PRODUCT+ " to assign product to manufacturer");
    System.out.println(ADD_MANUFACTURER+ " to add manufacturer");
    System.out.println(CLERKMENU + " to  switch to the clerk menu");
    System.out.println(HELP + " for help");
  }

  public void clerkmenu() {
    (WarehouseContext.instance()).changeState(0);
  }

   public void assignProduct()
   {
        boolean result;
        System.out.println("Assigning the Product To Manufacturer\n");
        String pid = getToken("Enter Product Id :");
        String mid = getToken("Enter Manufacturer Id :");
        result = warehouse.assignProductToManufacturer(pid, mid);

        if(result == true){
            System.out.println("Assigned the product.");
        }
        else
        {
            System.out.println("Error");
        }
   }

public void modifyPrice(){
  String pid = getToken("Enter product ID: ");
  Product product = warehouse.searchProduct(pid);
  if(product == null){
      System.out.println("Product does not exists");
      return;
  }
  
  double price = getFloat("Enter the new price: ");
  
  if(warehouse.modifyPrice(pid, price) == false){
      System.out.println("Could not update price");
  }
  else
  {
      System.out.println("Updated the new price.");
  }
}

public void addManufacturer()
    {
        String name = getToken("Enter manufacturer: ");
        String address = getToken("Enter address: ");
        String phone = getToken("Enter phone number: ");
        Manufacturer result;
        result = warehouse.addManufacturer(name, address, phone);

        if(result == null)
        {
            System.out.println("Error");
        }

        System.out.println(result);
    }

  

  public void logout() {
    (WarehouseContext.instance()).changeState(2); 
  }



  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case MODIFY_PRICE:                  modifyPrice();
                                            break;
        case ASSIGN_PRODUCT:                assignProduct();
                                            break;
        case ADD_MANUFACTURER:              addManufacturer();
                                            break;
        case CLERKMENU:                     clerkmenu();
                                            break;
        case HELP:                          help();
                                            break;
      }
    }
    logout();
  }


  public void run() {
    process();
  }
}
