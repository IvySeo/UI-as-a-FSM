import java.util.*;
import java.text.*;
import java.io.*;

public class Shipment implements Serializable{
    private static final long serialVersionUID = 1L;
    private Manufacturer manufacturer;
    private List orderedProducts = new LinkedList();
    private double supplierPrice;
    private String id;
    private static final String SHIPMENT_STRING = "S";
    
    public Shipment(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
        id = SHIPMENT_STRING + (ShipmentIdServer.instance()).getId();
    }
    
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    
    public void addProduct(ManufacturerOrder o){
        orderedProducts.add(o);
    }
    
    public String getId(){
        return id;
    }
    
    public Iterator getProducts(){
        return orderedProducts.iterator();
    }
    
    public double getSupplierPrice() {
        return supplierPrice;
    }
    
    public void setSupplierPrice(double s) {
        supplierPrice = s;
    }
}
