import java.util.*;
import java.io.*;

public class ManufacturerOrder implements Serializable
{
    private static final long serialVersionUID = 1L;
    private double totalCost = 0.0;
    private int orderedQuantity;
    private Product product;
    
    /**
     * Constructor for objects of class ManufacturerOrder
     */
    public ManufacturerOrder(Product p, int quantity)
    {
        this.product = p;
        this.orderedQuantity = quantity;
        
        this.totalCost = p.getSalesPrice() * quantity;
    }

    public double getTotalCost(){
        return totalCost;
    }
    
    public Product getProduct(){
        return product;
    }
    
    public int getOrderedQuantity(){
        return orderedQuantity;
    }
    
    public void setOrderedQuantity(int amount){
        orderedQuantity = amount;
    }
    
    public void setTotalCost(double newCost){
        totalCost = newCost;
    }
    
    public String toString(){
        String string = "Product (PID: " + product.getId() + "): | " + product.getName() + " | OrderedQty: "
                        + orderedQuantity + " | TotalCost $" + totalCost;
        return string;
    }
}
