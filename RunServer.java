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
        Server server = new Server(3333, baseServer);
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
            while (!this.baseServer.getStatusStartGame()){
                Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                BaseClient baseClient = (BaseClient) in.readObject();
                String ipAddress = socket.getInetAddress().getHostAddress();
                this.baseServer.setUser(baseClient, ipAddress);
                System.out.println(baseClient.getStatusPlayGame());
                // ถ้าผู้เล่นกดพร้อมและเกมยังไม่เริ่ม ถ้าเริ่มเกมไปแล้วจะไม่ทำงาน
                if(baseClient.getStatusPlayGame()){
                    this.baseServer.StartStatusGame();
                }
                in.close();
                socket.close();
                try {Thread.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
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
        while (this.baseServer.getTime() >= 0.0){
            try{
                this.baseServer.setTime(this.baseServer.getTime()-1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        // System.out.println("หยุดทำงาน");
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
        while (this.baseClient.getStatusPlayGame()) {
            if(this.baseServer.getTime()<=0){
                this.baseClient.setStatusPlayGame(false);
            }
            try{
                this.baseClient.setTime(this.baseServer.getTime());
                Socket socket = new Socket(this.ipAddress,this.baseClient.getPortServerClient());
                ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
                send.writeObject(this.baseClient);
                send.flush();

                send.close();
                socket.close();
            } catch (Exception e) {System.out.println(e);}
            try {Thread.sleep(10);} catch (Exception e) {}
        }
    }
}

