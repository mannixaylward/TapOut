import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Usage: TapOut <server> <port> <pagerID> <message>");
            System.exit(0);
        }
        /*
        else {
            try {
                firstArg = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }
        */
        Transmission transmission = new Transmission();
        //transmission.setIPAddress("192.168.0.20");
        //transmission.setIPPort(6000);
        //transmission.setPagerID("1700150");
        //transmission.setPagerMSG("ABC");
        transmission.setIPAddress(args[0]);
        transmission.setIPPort(Integer.parseInt(args[1]));
        transmission.setPagerID(args[2]);
        transmission.setPagerMSG(args[3]);
        transmission.send();
    
        transmission = null;
    }
  
}
