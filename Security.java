import java.util.*;
import java.text.*;
import java.io.*;

public class Security{
    private static Warehouse warehouse;
    
    public Security(){
        warehouse = Warehouse.instance();
    }
    
    public boolean validateClerk(String userID){
        if (warehouse.searchClient(userID) != null){
            return true;
        }else
            return false;
    }
}

