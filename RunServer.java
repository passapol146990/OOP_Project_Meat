import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * InnerServer
 */
public class RunServer {
    public static void main(String[] args) {
        BaseServer baseServer = new BaseServer();
        Countdown cd = new Countdown(baseServer);
        cd.start();
        Server server = new Server(3333, baseServer );
        server.start();
    }
}

class Server extends Thread{
    ServerSocket serverSocket;
    private int port;
    private BaseServer baseServer;
    Server(int port, BaseServer baseServer){
        this.port = port;
        this.baseServer = baseServer;
    }
    public void run(){
        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("Start Server PORT : "+this.port);
            while (true){
                Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) in.readObject();
                // String ipAddress = socket.getInetAddress().toString();
                String ipAddress = socket.getInetAddress().getHostAddress();
                System.out.println(ipAddress);
                this.baseServer.setUser(baseClient, ipAddress);
                // ถ้าผู้เล่นกดพร้อมและเกมยังไม่เริ่ม ถ้าเริ่มเกมไปแล้วจะไม่ทำงาน
                if(!this.baseServer.getStatusStartGame()){
                    if(baseClient.getStatusPlayGame()&&!this.baseServer.getStatusStartGame()){
                        this.baseServer.StartStatusGame();
                    }
                }
                in.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class Countdown extends Thread {
    BaseServer baseServer;
    Countdown(BaseServer baseServer){
        this.baseServer = baseServer;
    }
    public void run(){
        while (this.baseServer.getTime() > 0.0){
            try{
                this.baseServer.setTime(this.baseServer.getTime()-100);
                Thread.sleep(100);
            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        this.baseServer.setStatusStartGame(false);
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
        while (this.baseServer.getStatusStartGame()) {
            // System.out.println("time : "+this.baseServer.getTime());
            Socket socket;
            ObjectOutputStream send;
            try{
                this.baseClient.setTime(this.baseServer.getTime());
                socket = new Socket(this.ipAddress,this.baseClient.getPortServerClient());
                send = new ObjectOutputStream(socket.getOutputStream());
                send.writeObject(this.baseClient);
                send.flush();

                send.close();
                socket.close();
                Thread.sleep(100);
            } catch (Exception e) {System.out.println(e);}
            try {Thread.sleep(1);} catch (Exception e) {}
        }
    }
}

