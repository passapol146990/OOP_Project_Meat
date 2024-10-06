import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * InnerServer
 */
public class RunServer {
    public static void main(String[] args) {
        BaseServer baseServer = new BaseServer();
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
                String ipAddress = socket.getInetAddress().toString();
               this.baseServer.setUser(baseClient, ipAddress);
            //    BaseClient baseClient = this.baseServer.getUser().get(ipAddress);
            //    if(this.baseServer.getUser().get(ipAddress).getStatusPlayGame()){
            //    if(baseClient.getStatusPlayGame()){
            //     System.out.println(baseClient.getStatusPlayGame());
            //    }
            //    System.out.println(baseClient.getID());
            //    System.out.println(this.baseServer.getUser().size());
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
        float time = this.baseServer.getTime();
        while (time > 0.0){
            try{
                this.baseServer.setTime(time-100);
                Thread.sleep(100);
            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }

}