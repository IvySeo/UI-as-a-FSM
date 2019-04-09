// Author: Ivy Seo 
// Stage 1

import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private String address;
  private String phone;

  private String id;
  private double balance;
  private Invoice invoice;
  
  //List of orders made by a client
  private List orderList = new LinkedList();
  
  private static final String CLIENT_STRING = "C";

  public Client (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    balance = 0.0;
    invoice = new Invoice();
    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }
  
  public void addToInvoice(Order order, double cost){
      //Add order (product) to invoice
      invoice.addOrder(order);
      
      //Update client's balance
      double newBalance = invoice.getBalance() + cost;
      invoice.setBalance(newBalance);
  }

  public String getName() {
    return name;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public String getAddress() {
    return address;
  }
  
  public String getId() {
    return id;
  }
  
  public double getBalance(){
      return balance;
  }
  
  public void setName(String newName) {
    name = newName;
  }
  
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  
  public void setBalance(double newBalance){
      balance = newBalance;
  }
  
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  
  public Iterator getOrders()
  {
	  return invoice.getOrders();
  }
  
  public String toString() {
    String string = "CID: " + id + " | Client: " + name + " | Address: " + address + " | Phone: " + phone + " | Balance Due: $" + balance;
    return string;
  }
}
