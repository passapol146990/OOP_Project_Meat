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
        Socket socket;
        try{
            socket = new Socket(this.ip, this.port);
            ObjectOutputStream send = new ObjectOutputStream(socket);
            socket.close();
        }catch(IOException e){e.printStackTrace();}
    }
}
class OpenPortClient extends Thread{
    OpenPortClient(){}
    public void run(){
        while (true) {
            try{
                ServerSocket server = new ServerSocket(3333);
                server.accept();
            }catch(IOException e){}
        }
    }
}