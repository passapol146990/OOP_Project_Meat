import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * InnerServer
 */
public class RunServer {
    public static void main(String[] args) {
        BaseServer baseServer = new BaseServer();
        ServerRoby roby = new ServerRoby(baseServer);
        roby.start();
    }
}

class ServerRoby extends Thread{
    private ServerSocket serverSocket;
    private BaseServer baseServer;
    ServerRoby(BaseServer baseServer){
        this.baseServer = baseServer;
    }
    public void run(){
        try{
            serverSocket = new ServerSocket(this.baseServer.port);
            System.out.println("Start Server PORT : "+this.baseServer.port);
            while (this.baseServer.getStatusInRoby()){
                Socket socket = serverSocket.accept();
                String ipAddress = socket.getInetAddress().getHostAddress();
                ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) req.readObject();
                this.baseServer.setClient(baseClient, ipAddress);
                if(this.baseServer.controller_client.get(ipAddress)==null||!this.baseServer.controller_client.get(ipAddress)){
                    this.baseServer.controller_client.put(ipAddress, true);
                    SendClient client = new SendClient(ipAddress,4444,this.baseServer);
                    client.start();
                }
                req.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
class SendClient extends Thread{
    private String ipAddress;
    private int port;
    private BaseServer baseServer;
    SendClient(String ipAddress, int port, BaseServer baseServer){
        this.ipAddress = ipAddress;
        this.port = port;
        this.baseServer = baseServer;
    }
    public void run(){
        // try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        while(this.baseServer.controller_client.get(this.ipAddress)){
            try{
                Socket socket = new Socket(this.ipAddress,this.port);
                ObjectOutputStream res = new ObjectOutputStream(socket.getOutputStream());
                res.writeObject(this.baseServer);
                res.flush();
                res.reset();
                res.close();
                socket.close();
                try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            } catch (Exception e) {
                this.baseServer.controller_client.put(this.ipAddress,false);
            }
        }
    }
}