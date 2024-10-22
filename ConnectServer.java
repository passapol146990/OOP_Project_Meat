import java.io.IOException;
import java.io.ObjectOutputStream;
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
class OpenPortClient extends Thread{
    private App app;
    private int port;
    private String ip;
    OpenPortClient(App app, String ip, int port){
        this.app = app;
        this.ip = ip;
        this.port = port;
    }
    public void run(){
        while (true) {
            try{
                ServerSocket server = new ServerSocket(3333);
                server.accept();
            }catch(IOException e){}
        }
    }
}