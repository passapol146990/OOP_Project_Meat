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
            System.out.println("Client OPEN Port : "+this.port);
            serverSocket = new ServerSocket(this.port);
            while(true){
                if(this.baseClient.statusConnectServer){
                    Socket socket = serverSocket.accept();
                    ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                    BaseServer baseServer = (BaseServer) req.readObject();
                    this.app.baseServer = baseServer;
                    if(this.baseClient.statusConnectServer){
                        this.app.getBaseClient().setTime(baseServer.time);
                        if(baseServer.getStatusInRoby()){
                            this.app.getBaseClient().nowPage = "lobby";
                        }else if(baseServer.getStatusInGame()){
                            this.app.getBaseClient().nowPage = "start";
                        }else if(baseServer.getStatusEndGame()&&this.app.getBaseClient().statusReady){
                            this.app.getBaseClient().nowPage = "showscore";
                            this.app.getBaseClient().statusConnectServer = false;
                            this.app.getBaseClient().statusReady = false;
                            if(this.app.getBaseClient().getMeat()!=null){
                                this.app.getBaseClient().getMeat().kill();
                            }
                            this.app.getSound().closeEffect();
                            this.app.getSound().stopMusic();
                        }
                        this.app.getBaseClient().setOrder(baseServer.getClientByID(this.app.getBaseClient().id).getOrder());
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