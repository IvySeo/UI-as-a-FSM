import java.io.*;
public class ShipmentIdServer implements Serializable {
  private int idCounter;
  private static ShipmentIdServer server;
  private ShipmentIdServer() {
    idCounter = 1;
  }
  
  public static ShipmentIdServer instance() {
    if (server == null) {
      return (server = new ShipmentIdServer());
    } else {
      return server;
    }
  }
  
  public int getId() {
    return idCounter++;
  }
  
  public String toString() {
    return ("IdServer" + idCounter);
  }

  public static void retrieve(ObjectInputStream input) {
    try {
      server = (ShipmentIdServer) input.readObject();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  private void writeObject(java.io.ObjectOutputStream output) throws IOException {
    try {
      output.defaultWriteObject();
      output.writeObject(server);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
    try {
      input.defaultReadObject();
      if (server == null) {
        server = (ShipmentIdServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
}