import java.util.*;
import java.io.*;
public class ShipmentList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List shipments = new LinkedList();
  private static ShipmentList shipmentList;
  
  private ShipmentList() {
  }
  
  public static ShipmentList instance() {
    if (shipmentList == null) {
      return (shipmentList = new ShipmentList());
    } else {
      return shipmentList;
    }
  }

  public boolean insertShipment(Shipment shipment) {
    shipments.add(shipment);
    return true;
  }
  
  public void removeShipment(Shipment shipment){
      shipments.remove(shipment);
  }
  
  public Shipment search(String id) {
    Iterator allShipments = getShipments();
    while(allShipments.hasNext()){
        Shipment shipment = (Shipment)(allShipments.next());
        
        if(shipment.getId().equals(id)){
            return shipment;
        }
    }
    
    return null;
  }
  
  public Iterator getShipments(){
     return shipments.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(shipmentList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (shipmentList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (shipmentList == null) {
          shipmentList = (ShipmentList) input.readObject();
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
    return shipments.toString();
  }
}