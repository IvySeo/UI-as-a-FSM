
import java.util.*;
import java.io.*;
public class WaitList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List orders = new LinkedList();
  private static WaitList WaitList;
  
  private WaitList() {
  }
  
  public static WaitList instance() {
    if (WaitList == null) {
      return (WaitList = new WaitList());
    } else {
      return WaitList;
    }
  }

  public boolean insertOrder(Order order) {
    orders.add(order);
    return true;
  }
  
  public void removeOrder(Order order){
      orders.remove(order);
  }

  public Iterator getOrders(){
     return orders.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(WaitList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (WaitList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (WaitList == null) {
          WaitList = (WaitList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  
  public String toString() {
    return orders.toString();
  }
}
