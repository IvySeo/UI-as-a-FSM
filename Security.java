import java.util.*;
import javax.swing.LookAndFeel;
import java.text.*;
import java.io.*;
public class Security implements Serializable{
	
	public boolean validateClerk(String userName){
		if (WareHouse.instance().searchMembership(userID) != null){
			return true;
		}else
			return false;
}
