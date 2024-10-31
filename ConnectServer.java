import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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
        System.out.println("Client Connect : "+this.ip+" Port : "+this.port);
        try{
            while (this.app.getBaseClient().statusConnectServer) {
                Socket socket = new Socket(this.ip,this.port);
                ObjectOutputStream res = new ObjectOutputStream(socket.getOutputStream());
                res.writeObject(this.app.getBaseClient());
                res.flush();
                res.reset();
                res.close();
                socket.close();
                try {Thread.sleep(100);} catch (InterruptedException e) {throw new Exception(e);}
            }
        } catch (Exception e) {
            System.out.println(e+", Stop Connect Server : "+this.ip+" "+this.port);
        }
        try {Thread.sleep(500);} catch (InterruptedException e) {}
        CheckOutServer checkout = new CheckOutServer(this.app, this.ip, this.port);
        checkout.start();
    }
}
class CheckOutServer extends Thread{
    private App app;
    private int port;
    private String ip;
    CheckOutServer(App app, String ip, int port){
        this.app = app;
        this.ip = ip;
        this.port = port;
        System.out.println("Check Out Server : "+this.ip+" "+this.port);
    }
    public void run(){
        try{
            Socket socket = new Socket(this.ip,this.port);
            ObjectOutputStream res = new ObjectOutputStream(socket.getOutputStream());
            res.writeObject(this.app.getBaseClient());
            res.flush();
            res.reset();
            res.close();
            socket.close();
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new Exception(e);}
        } catch (Exception e) {
            this.app.getBaseClient().statusConnectServer = false;
        }
        this.app.getBaseClient().statusConnectServer = false;
        this.app.getBaseClient().statusReady = false;
        this.app.getBaseClient().nowPage = "menu";
    }
}