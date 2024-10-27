import java.io.*;
import java.net.*;
import java.util.*;;
class OpenPortClient extends Thread{
    private ServerSocket serverSocket;
    private BaseClient baseClient;
    private App app;
    private int port;
    OpenPortClient(App app,BaseClient baseClient, int port){
        this.app = app;
        this.baseClient = baseClient;
        this.port = port;
    }
    public void run(){
        try{
            System.out.println("open port : "+this.port);
            serverSocket = new ServerSocket(this.port);
            while(true){
                if(this.baseClient.statusConnectServer){
                    Socket socket = serverSocket.accept();
                    ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                    this.app.baseServer = (BaseServer) req.readObject();
                    req.close();
                    socket.close();
                }
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}