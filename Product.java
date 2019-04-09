// Author: Ivy Seo 
// Stage 1

import java.util.*;
import java.lang.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private int quantity;
  private double salesPrice;
  private String id;
  
  private List suppliers = new LinkedList();
  private static final String PRODUCT_STRING = "P";

  //Constructor
  public Product(String name, int quantity, double salesPrice) {
    this.name = name;
    this.quantity = quantity;
    this.salesPrice = salesPrice;
    id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
  }

  // invokes funcion getProductID()
  public String getId() { 
    return id;
  }
  
  public String getName() {
    return name; 
  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public double getSalesPrice() {
    return salesPrice;
  }
  
  
  public void setName(String newName) {
    name = newName;
  }
  
  public void setQuantity(int newQuantity) {
    quantity = newQuantity;
  }
  
  public void setSalesPrice(double newSalesPrice) {
    salesPrice = newSalesPrice;
  }
  
  public void updateQuantity(int amount){
      quantity += amount;
  }
  
  public void assignManufacturer(Manufacturer manufacturer) {
    suppliers.add(manufacturer);
  }
  
  public void unassignManufacturer(Manufacturer manufacturer) {
    suppliers.remove(manufacturer);
  }
  
  
  public Iterator getProviders(){
   return suppliers.iterator();
  }
  
  public boolean equals(String id) {
    return this.id.equals(id);
  }

  public String toString() {
    String string = "PID: " + id + " | Product: " + name + " | Quantity: " + quantity + " | salesPrice $" + salesPrice;
    return string;  
  }
}
