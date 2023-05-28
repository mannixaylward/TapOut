import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
        
        Transmission transmission = new Transmission();
        transmission.setIPAddress("192.168.0.20");
        transmission.setIPPort(6000);
        transmission.setPagerID("1700150");
        transmission.setPagerMSG("ABC");
    
        /*
        System.out.println("IPAdress = " + transmission.getIPAddress());
        System.out.println("IPPort   = " + transmission.getIPPort());
        System.out.println("PagerID  = " + transmission.getPagerID());
        System.out.println("PagerMSG = " + transmission.getPagerMSG());
        */
    
        transmission.send();
    
        transmission = null;
    }
  
}
