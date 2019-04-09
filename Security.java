import java.util.*;
import javax.swing.LookAndFeel;
import java.text.*;
import java.io.*;
public class Security implements Serializable{
	
	private Warehouse warehouse;
	
	public boolean validateClerk(String userID)
	{
		if (warehouse.instance().searchClient(userID) != null)
		{
			return true;
		}
		else
			return false;
		
	
	}
}

