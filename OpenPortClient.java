import java.io.*;
import java.net.*;
import java.util.*;;
class OpenPortClient extends Thread{
    private ServerSocket serverSocket;
    private BaseClient baseClient;
    private int port;
    OpenPortClient(BaseClient baseClient, int port){
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
                    BaseServer baseServer = (BaseServer) req.readObject();
                    ArrayList<HashMap<String,String>> x = baseServer.getPlayerInRobby();
                    for(int i=0;i<x.size();i++){
                        System.out.println("ชื่อร้าน : "+x.get(i).get("name")+", สถานะ : "+x.get(i).get("status"));
                    }
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