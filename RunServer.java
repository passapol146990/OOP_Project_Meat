import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * InnerServer
 */
public class RunServer {
    public static void main(String[] args) {
        BaseServer baseServer = new BaseServer();
        Server roby = new Server(baseServer);
        roby.start();
    }
}

class Server extends Thread{
    private ServerSocket serverSocket;
    private BaseServer baseServer;
    Server(BaseServer baseServer){
        this.baseServer = baseServer;
    }
    public void run(){
        try{
            serverSocket = new ServerSocket(this.baseServer.port);
            System.out.println("Start Server PORT : "+this.baseServer.port);
            while (true){
                Socket socket = serverSocket.accept();
                String ipAddress = socket.getInetAddress().getHostAddress();
                ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                // อัพเดทข้อมูลของผู้เล่น
                BaseClient baseClient = (BaseClient) req.readObject();
                this.baseServer.setClient(baseClient, ipAddress);
                // ตรวจสอบถ้าไม่มีผู้เล่นเชื่อมต่อ server จะให้ server กลับมาหน้า lobby
                if(this.baseServer.CountPlayerOnServer==0){
                    this.baseServer.setStatusInRoby(true);
                }
                // ถ้ายังไม่มี ip ผู้เล่นคนนี้หรือผู้เล่นคนนี้หลุดการเชื่อมต่อจะให้ทำงานตรงนี้ ถ้ามีแล้วจะไม่ทำงาน
                if(this.baseServer.controller_client.get(ipAddress)==null||!this.baseServer.controller_client.get(ipAddress)){
                    // เก็บข้อมูลว่าผู้เล่นคนนี้เชื่อมต่ออยู่
                    this.baseServer.controller_client.put(ipAddress, true);
                    this.baseServer.IDClientGETIPAddress.put(baseClient.id,ipAddress);
                    // บวก 1 สำหรับคนที่ออนไลน์
                    this.baseServer.CountPlayerOnServer += 1;
                    // สร้าง Thread ส่ง BaseServer กลับไปหาผู้เล่นคนนั้น
                    SendClient client = new SendClient(ipAddress,4444,this.baseServer);
                    client.start();
                }
                // ถ้ายังอยู่ที่ lobby จะให้มาทำงานที่นี่
                if(this.baseServer.getStatusInRoby()){
                    // ตรวจสอบว่าคนที่อยู่ใน lobby กดพร้อมและจำนวนคนเล่นครบตาม server กำหนดหรือยัง
                    this.baseServer.checkReadyPlayer();
                }else if(this.baseServer.getStatusInGame()){
                    this.baseServer.checkDataBasePlayerInGame();
                }
                req.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
// คราสสำหรับส่งข้อมูลกลับไปให้ผู้เล่น
class SendClient extends Thread{
    private String ipAddress;
    private int port;
    private BaseServer baseServer;
    SendClient(String ipAddress, int port, BaseServer baseServer){
        this.ipAddress = ipAddress;
        this.port = port;
        this.baseServer = baseServer;
    }
    public void run(){
        while(this.baseServer.controller_client.get(this.ipAddress)){
            try{
                Socket socket = new Socket(this.ipAddress,this.port);
                ObjectOutputStream res = new ObjectOutputStream(socket.getOutputStream());
                res.writeObject(this.baseServer);
                res.flush();
                res.reset();
                res.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            } catch (Exception e) {
                this.baseServer.controller_client.put(this.ipAddress,false);
                this.baseServer.CountPlayerOnServer -= 1;
            }
        }
        System.out.println(this.ipAddress+" ออกจากเซิฟไปแล้ว");
    }
}