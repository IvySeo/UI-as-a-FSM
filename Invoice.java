import java.util.*;
import java.text.*;
import java.io.*;

//Not needed for stage 1 processes. So this class may be incomplete
public class Invoice implements Serializable{
    private static final long serialVersionUID = 1L;
    private Client client;
    private List orders = new LinkedList();
    private double balance;
    
    public Invoice(){
        balance = 0.0;
    }
    
    public void addOrder(Order o){
        orders.add(o);
    }
    
    public Iterator getOrders() {
    	return orders.iterator();
    	
    }
    
    public Client getClient(){
        return client;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double newBalance) {
        balance = newBalance;
    }
}
