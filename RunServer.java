import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

class Server extends Thread{
    private BaseServer baseServer;
    Server(BaseServer baseServer){
        this.baseServer = baseServer;
    }
    public void run(){
        while (true) {
            if(this.baseServer.getStatusInRoby()){
                ServerRoby roby = new ServerRoby(baseServer);
                roby.start();
            }else{

            }
        }
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
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) in.readObject();
                if(this.baseServer.controller_client.get(ipAddress)==null){
                    this.baseServer.setUser(baseClient, ipAddress);
                    this.baseServer.controller_client.put(ipAddress, true);
                    Controller_client control = new Controller_client(ipAddress,3344,this.baseServer);
                    control.start();
                }
                in.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
class Controller_client extends Thread{
    private String ip;
    private int port;
    private BaseServer baseServer;
    Controller_client(String ip, int port, BaseServer base){
        this.ip = ip;
        this.port = port;
        this.baseServer = base;
    }
    public void run(){
        System.out.println("Controller_client "+this.ip);
        
    }
}
class ResponseServerRoby implements Serializable{
    private HashMap<String,String> data;
    ResponseServerRoby(HashMap<String,String> data){
        this.data = data;
    }
    ArrayList<String> getUsernameInRoby(){
        ArrayList<String> user = new ArrayList<String>();
        // for(String i : this.data.keySet()){
        //     user.add(this.data.get(i).getName());
        // }
        return user;
    }
}
class ServerStartGamer extends Thread{
    private BaseClient baseClient;
    private String ipAddress;
    private BaseServer baseServer;
    ServerStartGamer(BaseServer baseServer,String ipAddress,BaseClient client){
        this.baseServer = baseServer;
        this.ipAddress = ipAddress;
        this.baseClient = client;
    }
    public void run(){
        // while (this.baseClient.getStatusPlayGame()) {
        //     if(this.baseServer.getTime()<=0){
        //         this.baseClient.setStatusPlayGame(false);
        //     }
        //     try{
        //         this.baseClient.setTime(this.baseServer.getTime());
        //         Socket socket = new Socket(this.ipAddress,this.baseClient.getPortServerClient());
        //         ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
        //         send.writeObject(this.baseClient);
        //         send.flush();

        //         send.close();
        //         socket.close();
        //     } catch (Exception e) {System.out.println(e);}
        //     try {Thread.sleep(10);} catch (Exception e) {}
        // }
    }
}
class SendClient extends Thread{
    private boolean statusSend = true;
    private String userip;
    private int userport = 33333;
    private BaseServer baseServer;
    SendClient(String ip, BaseServer baseServer){
        this.userip = ip;
        this.baseServer = baseServer;
    }
    public void run(){
        // ResultServer
        // while (this.statusSend) {
        //     try{
        //         Socket socket = new Socket(this.userip,this.userport);
        //         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        //         out.writeObject(new ResponseServerRoby(this.baseServer.getUser()));
        //         out.close();
        //         socket.close();
        //     }catch(IOException e){
        //         System.out.println(e);
        //         this.statusSend = false;
        //     }
        // }
        // System.out.println(this.userip+" ไม่สามารถเชื่อมต่อได้");
    }
}