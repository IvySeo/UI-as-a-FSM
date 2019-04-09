//Author: Noah Coyle
//Stage 1

import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
  private static final long serialVersionUID = 1L;

  private ProductList productList;
  private ManufacturerList manufacturerList;
  private ClientList clientList;
  private WaitList waitList;
  private ShipmentList shipmentList;
  private static Warehouse warehouse;
  

  private Warehouse() {
    productList = productList.instance();
    manufacturerList = manufacturerList.instance();
    clientList = clientList.instance();
    waitList = waitList.instance();
    shipmentList = shipmentList.instance();
  }

  public static Warehouse instance() {
    if (warehouse == null) {
      ProductIdServer.instance(); // instantiate all singletons
      ClientIdServer.instance();
      ManufacturerIdServer.instance();
      OrderIdServer.instance();
      ShipmentIdServer.instance();
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }

  public Manufacturer addManufacturer(String Name, String Address, String PhoneNumber) {
    Manufacturer manufacturer = new Manufacturer(Name, Address, PhoneNumber);
    if (manufacturerList.insertManufacturer(manufacturer)) {
      return (manufacturer);
    }
    return null;
  }

  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address, phone);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }

  public Product addProduct(String name, int quantity, double price) {
    Product product = new Product(name, quantity, price);
    if (productList.insertProduct(product)) {
      return (product);
    }
    return null;
  }

  public boolean assignProductToManufacturer (String productID, String manufacturerID)
  {
    Manufacturer manufacturer = manufacturerList.search(manufacturerID);
    Product product = productList.search(productID);
    if(manufacturer != null && product != null){
        Iterator result = manufacturer.getProvidedProducts();
        
        boolean flag = false;
        while(result.hasNext()){
            Product temp = (Product)(result.next());
            if(temp.getId().equals(productID)){
                flag = true;
                break;
            }
        }
        
        if(flag == false){
            manufacturer.assignProduct(product);
            product.assignManufacturer(manufacturer);
            return true;
        }
    }
    return false;
  }

  public boolean unassignProductToManufacturer (String productID, String manufacturerID)
  {
    Manufacturer manufacturer = manufacturerList.search(manufacturerID);
    Product product = productList.search(productID);
    if(manufacturer != null && product != null){
        Iterator result = manufacturer.getProvidedProducts();
        if(result == null){
            return false;
        }
        
        boolean flag = false;
        while(result.hasNext()){
            Product temp = (Product)(result.next());
            if(temp.getId().equals(productID)){
                flag = true;
                break;
            }
        }
        
        if(flag == true){
            manufacturer.unassignProduct(product);
            product.unassignManufacturer(manufacturer);
            return true;
        }
    }
    return false;
  }

  public Iterator getClients() {
      return clientList.getClients();
  }

  public Iterator getManufacturers() {
      return manufacturerList.getManufacturers();
  }

  public Iterator<Product> getProducts() {
      return productList.getProducts();
  }

  public Iterator getSuppliersForProduct(String productID){
      if(productList.search(productID) != null){
          return (productList.search(productID)).getProviders();
      }
      return null;
  }

  public Iterator getProductsFromManufacturer(String ManufacturerID){
      if(manufacturerList.search(ManufacturerID) != null){
          return (manufacturerList.search(ManufacturerID)).getProvidedProducts();
      }
      return null;
  }

  //Stage 2
  public boolean addAndProcessOrder(String cid, String pid, int quantity){
      Client client = clientList.search(cid);
      Product product = productList.search(pid);
      
      if(product == null){
          return false;
      }
      
      //Create order
      Order order = new Order(client, product, quantity);
      
      //Check if enough is in stock
      if(product.getQuantity() >= quantity){
          //Put order on invoice
          double cost = product.getSalesPrice() * quantity;
          client.addToInvoice(order, cost);
          client.setBalance((cost + client.getBalance()));
          
          //Subtract from inventory
          productList.search(product.getId()).setQuantity((product.getQuantity() - quantity));
          System.out.println(pid + ": PRODUCT FULFILLED AND INVOICED | UPDATED CLIENT BALANCE: $" + client.getBalance());
      }
      else
      {
          //Put order on waitlist
          waitList.insertOrder(order);
          System.out.println(product.getId() + ": PRODUCT PUT ON WAITLIST");
      }
      return true;
  }

  public void placeOrderWithManufacturer(Shipment shipment)
  {
      shipmentList.insertShipment(shipment);
  }

  public double acceptClientPayment(String cid, double payment)
  {
      double newBalance = (clientList.search(cid)).getBalance() - payment;
      (clientList.search(cid)).setBalance(newBalance);
      return newBalance;
  }

  
  public Iterator getWaitListedOrdersForClient(String cid){
      if(clientList.search(cid) != null){
          return waitList.getOrders();
      }
      return null;
  }
 
  public Iterator getWaitListedOrdersForProduct(String pid){
      if(productList.search(pid) != null){
          return waitList.getOrders();
      }
      return null;
  }
  
  public Iterator getOrdersPlacedWithManufacturer(){
      return shipmentList.getShipments();
  }
  
  //Stage 3: Recieving a shipment
  public void receiveShipment(String sid)
  {
      //Check to see if shipment exists
      if(shipmentList.search(sid) == null){
          System.out.println("No Data for: " + sid);
          return;
      }
      
      //Obtain products for a particular order from shipmentList
      Iterator shipmentProducts = (shipmentList.search(sid)).getProducts();
      
      while(shipmentProducts.hasNext()){
          ManufacturerOrder m_order = (ManufacturerOrder)(shipmentProducts.next());
          String s_pid = m_order.getProduct().getId();
          int s_quantity = m_order.getOrderedQuantity() + (productList.search(s_pid)).getQuantity();
          
          //Obtain orders from waitlist
          Iterator waitListOrders = waitList.getOrders();
          
          
          /* False if shipment product does not fulfill waitlisted product. 
           * True if shipment product fulfills waitlisted item
          */
          boolean fulfilled = false;
          
          //Iterate through waitList
          while(waitListOrders.hasNext()){
              //Obtain an order from waitList
              Order order = (Order)(waitListOrders.next());
              
              String w_pid = order.getProduct().getId();
              int w_quantity = order.getOrderedQuantity();
              
              //if shipment product does exists on waitList
              if(s_pid.equals(w_pid)){
                  
                  //If waitListed product can be fullfilled
                  if(s_quantity >= w_quantity){
                      //Add product to client's invoice
                      Client client = clientList.search(order.getClient().getId());
                      double cost = m_order.getProduct().getSalesPrice()* w_quantity;
                      client.addToInvoice(order, cost);
                      client.setBalance((cost + client.getBalance()));
                  
                      System.out.println("FULFILLING WAITLISTED ITEM: " + order.toString());
                      
                      s_quantity = s_quantity - w_quantity;
                      
                      //Remove order from waitList
                      waitListOrders.remove();
                      fulfilled = true;
                  }
              }
          }
          
          if(fulfilled == false){
              System.out.println("(PID: " + s_pid + ") DID NOT FULFILL A WAITLISTED ITEM, ADDING TO INVENTORY");
          }
          
          productList.search(s_pid).setQuantity((s_quantity));
      }
      
      //Deletes shipment from shipmentList since shipment has been received
      shipmentList.removeShipment(shipmentList.search(sid));
  }
  
  public boolean modifyPrice(String pid, double newPrice){
      boolean flag = false;
      Product product = searchProduct(pid);
      
      if(product == null){
          return false;
      }
      
      product.setSalesPrice(newPrice);
      return true;
  }
  
  public Iterator getShipment(String sid){
      if(shipmentList.search(sid) != null){
          return shipmentList.search(sid).getProducts();
      }
      return null;
  }

  public Client searchClient(String id){
      return (clientList.search(id));
  }

  public Product searchProduct(String id){
      return (productList.search(id));
  }
  
  public Manufacturer searchManufacturer(String id){
      return (manufacturerList.search(id));
  }
    

  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ProductIdServer.retrieve(input);
      ManufacturerIdServer.retrieve(input);
      ClientIdServer.retrieve(input);
      OrderIdServer.retrieve(input);
      ShipmentIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }

  public static boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ProductIdServer.instance());
      output.writeObject(ManufacturerIdServer.instance());
      output.writeObject(ClientIdServer.instance());
      output.writeObject(OrderIdServer.instance());
      output.writeObject(ShipmentIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }

  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
