//Author: Sumaira Shahzad
//Stage 1

import java.util.*;
import java.io.*;
public class Manufacturer implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String MANUFACTURER_STRING = "M";
  private List productsSupplied = new LinkedList();
  private List assignedProducts = new LinkedList();
  private List orderList = new LinkedList();
  
  public Manufacturer (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    id = MANUFACTURER_STRING + (ManufacturerIdServer.instance()).getId();
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
  
  public Iterator getProvidedProducts(){
    return productsSupplied.iterator();
  }
  
  public String getId() {
    return id;
  }
  
  public void assignProduct(Product product){
    productsSupplied.add(product);
  }
  
  public void unassignProduct(Product product){
    productsSupplied.remove(product);
  }
  
  public void insertOrder(Product product){
      orderList.add(product);
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
  
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  
  public String toString() {
    String string = "MID: " + id + " | Manufacturer: " + name + " | Address: " + address + " | Phone: " + phone;
    return string;
  }
}
