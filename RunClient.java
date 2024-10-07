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
        String ipaddress = "127.0.0.1";
        int port = 3333;
        BaseClient baseClient = new BaseClient(port+1);
        Client client = new Client(ipaddress, port, baseClient);
        client.start();
        Input input = new Input(baseClient);
        input.start();
        ServerClient serverClient = new ServerClient(port+1, baseClient);
        serverClient.start();
    }
}

class Client extends Thread{
    private String ipaddress;
    private int port;
    private BaseClient client;
    Client(String ipaddress, int port,BaseClient client){
        this.ipaddress = ipaddress;
        this.port = port;
        this.client = client;
    }
    public void run(){
        Socket socket;
        ObjectOutputStream send;
        try{
            while (true) {
                socket = new Socket(this.ipaddress,this.port);
                send = new ObjectOutputStream(socket.getOutputStream());
                send.writeObject(this.client);
                send.flush();

                send.close();
                socket.close();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println(e);
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
            while (true){
                Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                this.baseClient = (BaseClient) in.readObject();
                System.out.println(this.baseClient.getTime());

                in.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
class Input extends Thread{
    private BaseClient baseClient;
    Input(BaseClient baseClient){
        this.baseClient = baseClient;
    }
    public void run(){
        while(true){
            Scanner sc = new Scanner(System.in);
            boolean x = sc.nextBoolean();
            this.baseClient.setStatusPlayGame(x);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    
}
