import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectServer extends Thread{
    private App app;
    private int port;
    private String ip;
    ConnectServer(App app, String ip, int port){
        this.app = app;
        this.ip = ip;
        this.port = port;
    }
    public void run(){
        System.out.println("Connect Server : "+this.ip+" "+this.port);
        try{
            while (this.app.getBaseClient().statusConnectServer) {
                Socket socket = new Socket(this.ip,this.port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(this.app.getBaseClient());
                out.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (Exception e) {
            System.out.println(e+", Stop Connect Server : "+this.ip+" "+this.port);
            this.app.getBaseClient().statusConnectServer = false;
        }
    }
}
class OpenPortClient_out_Game extends Thread{
    private App app;
    private int port;
    private String ip;
    OpenPortClient_out_Game(App app, String ip, int port){
        this.app = app;
        this.ip = ip;
        this.port = port;
    }
    @SuppressWarnings("resource")
    public void run(){
        ServerSocket serverSocket;
        while(true){
            try{
                serverSocket = new ServerSocket(this.port);
                Socket socket = serverSocket.accept();
                ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) req.readObject();
                req.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class OpenPortClient_in_Game extends Thread{
    private App app;
    private int port;
    private String ip;
    OpenPortClient_in_Game(App app, String ip, int port){
        this.app = app;
        this.ip = ip;
        this.port = port;
    }
    @SuppressWarnings("resource")
    public void run(){
        ServerSocket serverSocket;
        while(true){
            try{
                serverSocket = new ServerSocket(this.port);
                Socket socket = serverSocket.accept();
                ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) req.readObject();
                req.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}