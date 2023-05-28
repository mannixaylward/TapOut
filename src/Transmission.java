import java.io.*;
import java.net.*;
import java.time.LocalDateTime;  

public class Transmission {
    private String pagerID;
    private String pagerMSG;
    private String IPAddress;
    private int IPPort;

    public String getPagerID() {
        return this.pagerID;
    }

    public void setPagerID(String pagerID) {
        this.pagerID = pagerID;
    }

    public String getPagerMSG() {
        return this.pagerMSG;
    }

    public void setPagerMSG(String pagerMSG) {
        this.pagerMSG = pagerMSG;
    }

    public String getIPAddress() {
        return this.IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getIPPort() {
        return this.IPPort;
    }

    public void setIPPort(int IPPort) {
        this.IPPort = IPPort;
    }

    public String checksum(String data) {
        int sum = 0;
        for (int i = 0; i < data.length(); i++) {
            sum += (int)data.charAt(i);
        }

        int d3 = (sum & 0x0f) + 0x30;
        sum >>= 4;
        int d2 = (sum & 0x0f) + 0x30;
        sum >>= 4;
        int d1 = (sum & 0x0f) + 0x30;

        String checksum = String.valueOf((char)d1) + String.valueOf((char)d2) + String.valueOf((char)d3);
        return checksum;
    }

    public void send() throws Exception {
        System.out.println(LocalDateTime.now());
        System.out.println("IPAdress = " + IPAddress);
        System.out.println("IPPort   = " + IPPort);
        System.out.println("PagerID  = " + pagerID);
        System.out.println("PagerMSG = " + pagerMSG);
        String LOG = "";
        byte[] buffer = new byte[1024];
        
        //*** Connect to server
        Socket clientSocket = new Socket(this.IPAddress, this.IPPort);

        //*** Initiate Session
        String data = "\r";
        System.out.println("OUT:<CR>");

        OutputStream outToServer = clientSocket.getOutputStream();
        outToServer.write(data.getBytes());
        InputStream inFromServer = clientSocket.getInputStream();

        inFromServer.read(buffer);
        LOG = new String(buffer).replaceAll("[^\\x20-\\x7E]", "");
        System.out.println("IN :" + LOG);

        //*** Session type = PG1
        data = "\u001BPG1\r";
        System.out.println("OUT:<ESC>PG1");
        outToServer.write(data.getBytes());
        inFromServer.read(buffer);
        LOG = new String(buffer).replaceAll("[^\\x20-\\x7E]", "");
        System.out.println("IN :" + LOG);

        //*** Prepare Payload
        data = "\u0002" + this.pagerID + "\r" + this.pagerMSG + "\r\u0003";
        String checksum = this.checksum(data);
        String payload = data + checksum + "\r";
        System.out.println("OUT:<STX>"+ pagerID + "<CR>" + pagerMSG + "<CR><ETX>" + checksum + "<CR>");

        //*** Send Payload
        outToServer.write(payload.getBytes());
        inFromServer.read(buffer);
        LOG = new String(buffer).replaceAll("[^\\x20-\\x7E]", "");
        System.out.println("IN :" + LOG);

        //*** End of Transmission
        data = "\u0004\r";
        System.out.println("OUT:<EOT><CR>");
        outToServer.write(data.getBytes());
        inFromServer.read(buffer);
        LOG = new String(buffer).replaceAll("[^\\x20-\\x7E]", "");
        System.out.println("IN :" + LOG);
        clientSocket.close();
    }
}
