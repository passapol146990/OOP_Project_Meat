import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
/**
 * InnerClient
 */
public class RunClient {
    public static void main(String[] args){
        Seting_Client seting_Client = new Seting_Client();
        BaseClient baseClient = new BaseClient(seting_Client.getPortServer()+1);
        Input input = new Input(baseClient,seting_Client);
        input.start();
    }
}

class Client extends Thread{
    private Seting_Client seting_Client;
    private BaseClient baseClient;
    Client(BaseClient baseClient, Seting_Client seting_Client){
        this.baseClient = baseClient;
        this.seting_Client = seting_Client;
    }
    public void run(){
        try{
            while (this.baseClient.getStatusConnectServer()) {
                Socket socket = new Socket(this.seting_Client.getIPAddressServer(),this.seting_Client.getPortServer());
                ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
                send.writeObject(this.baseClient);
                send.flush();

                send.close();
                socket.close();
                Thread.sleep(10);
            }
        } catch (Exception e) {
            System.out.println(e);
            this.baseClient.setStatusConnectServer(false);
        }
    }
}
class ServerClient extends Thread{
    ServerSocket serverSocket;
    private int port;
    private BaseClient baseClient;
    ServerClient(int port, BaseClient baseClient){
        this.port = port;
        this.baseClient = baseClient;
    }
    public void run(){
        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("Start Server PORT : "+this.port);
            while (!this.baseClient.getStatusConnectServer()){
                Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                BaseClient server_baseClient = (BaseClient) in.readObject();
                this.baseClient.setTime(server_baseClient.getTime());
                this.baseClient.setStatusPlayGame(server_baseClient.getStatusPlayGame());
                if(this.baseClient.getTime()<=0){
                    this.baseClient.setStatusPlayGame(false);
                }
                System.out.println(this.baseClient.getTime());

                in.close();
                socket.close();
                try {Thread.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
class Input extends Thread{
    private Seting_Client seting_Client;
    private BaseClient baseClient;
    Input(BaseClient baseClient, Seting_Client seting_Client){
        this.baseClient = baseClient;
        this.seting_Client = seting_Client;
    }
    public void run(){
        while(true){
            if(!this.baseClient.getStatusConnectServer()){
                Scanner sc = new Scanner(System.in);
                System.out.print("input : ");
                boolean status = sc.nextBoolean();
                if(status){
                    this.baseClient.setStatusConnectServer(status);
                    Client client = new Client(this.baseClient, this.seting_Client);
                    client.start();
                    // ServerClient serverClient = new ServerClient(seting_Client.getPortServer()+1, baseClient);
                    // serverClient.start();
                }
            }
            // this.baseClient.setStatusPlayGame(x);
            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    
}
