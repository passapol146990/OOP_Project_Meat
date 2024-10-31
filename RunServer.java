import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import java.awt.*;

public class RunServer {
    public static void main(String[] args) {
        PanelCreateServer server = new PanelCreateServer();
        server.setVisible(true);
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
            while (this.baseServer.createServer){
                Socket socket = serverSocket.accept();
                String ipAddress = socket.getInetAddress().getHostAddress();
                ObjectInputStream req = new ObjectInputStream(socket.getInputStream());
                // อัพเดทข้อมูลของผู้เล่น
                BaseClient baseClient = (BaseClient) req.readObject();
                this.baseServer.setClient(baseClient, ipAddress);
                // ตรวจสอบว่าผู้เล่นคนนั้นออกจากเซิฟหรือยัง ถ้าออกจากเซิฟเขาจะส่งค่า statusConnectServer = false มาแล้วจะทำงานตรงนี้
                if(baseClient.statusConnectServer==false){
                    this.baseServer.controller_client.put(ipAddress, false);
                    this.baseServer.CountPlayerOnServer -= 1;
                }else{
                    // ถ้ายังไม่มี ip ผู้เล่นคนนี้หรือผู้เล่นคนนี้หลุดการเชื่อมต่อจะให้ทำงานตรงนี้ ถ้ามีแล้วจะไม่ทำงาน
                    if(this.baseServer.controller_client.get(ipAddress)==null||this.baseServer.controller_client.get(ipAddress)==false){
                        // เก็บข้อมูลว่าผู้เล่นคนนี้เชื่อมต่ออยู่
                        this.baseServer.controller_client.put(ipAddress, true);
                        this.baseServer.IDClientGETIPAddress.put(baseClient.id,ipAddress);
                        // บวก 1 สำหรับคนที่ออนไลน์
                        this.baseServer.CountPlayerOnServer += 1;
                        // สร้าง Thread ส่ง BaseServer กลับไปหาผู้เล่นคนนั้น
                        SendClient client = new SendClient(ipAddress,4444,this.baseServer);
                        client.start();
                        System.out.println(baseClient.getNameShop()+", IP : "+ipAddress+" เข้าร่วมเซิฟเวอร์ มีผู้เล่นทั้งหมด : "+this.baseServer.CountPlayerOnServer);
                    }
                    // ถ้ายังอยู่ที่ lobby จะให้มาทำงานที่นี่
                    if(this.baseServer.getStatusInRoby()){
                        // ตรวจสอบว่าคนที่อยู่ใน lobby กดพร้อมและจำนวนคนเล่นครบตาม server กำหนดหรือยัง
                        this.baseServer.checkReadyPlayer();
                    }else if(this.baseServer.getStatusInGame()){
                        this.baseServer.checkDataBasePlayerInGame();
                    }
                }
                req.close();
                socket.close();
                try {Thread.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        }catch(IOException | ClassNotFoundException e){System.out.println(e);}
        System.out.println("x");
    }
}
class CheckPlayerInServer extends Thread{
    private BaseServer baseServer;
    CheckPlayerInServer(BaseServer baseServer){
        this.baseServer = baseServer;
    }
    public void run(){
        while (this.baseServer.createServer) {
            try {Thread.sleep(100);}catch (InterruptedException e) {e.printStackTrace();}
            // ตรวจสอบถ้าไม่มีผู้เล่นเชื่อมต่อ server จะให้ server กลับมาหน้า lobby
            int countPlayer = 0;
            for(Boolean i : this.baseServer.controller_client.values()){
                if(i){countPlayer+=1;}
            }
            this.baseServer.CountPlayerOnServer = countPlayer;
            if(this.baseServer.CountPlayerOnServer==0){
                this.baseServer.statusInRoby = true;
                this.baseServer.statusEndGame = false;
                this.baseServer.statusInGame = false;
            }
        }
    }
}
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
                try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
            } catch (Exception e) {
                this.baseServer.controller_client.put(this.ipAddress,false);
            }
        }
        int countPlayer = 0;
        for(Boolean i : this.baseServer.controller_client.values()){
            if(i){countPlayer+=1;}
        }
        this.baseServer.CountPlayerOnServer = countPlayer;
        System.out.println(this.ipAddress+" ออกจากเซิฟไปแล้ว มีผู้เล่นเหลืออยู่ : "+this.baseServer.CountPlayerOnServer);
    }
}
class PanelCreateServer extends JFrame{
    private BaseServer baseServer = new BaseServer();
    private String ip;
    PanelCreateServer(){
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(150,10,300,300);
        //-------------------------[ create component]------------------------------------------//
        JPanel panel = new JPanel();
        JLabel titleShowIP = new JLabel("");
        JLabel titleCountPlayer = new JLabel("Count Player : ");
        JLabel titleTime = new JLabel("Time : ");
        JLabel titleShowStatus = new JLabel("ยังไม่ทำงาน");
        JTextField inputCountPlayer = new JTextField("3");
        JTextField inputTime = new JTextField("300");
        JButton runServer = new JButton("Start server");
        //-------------------------[ Set position component]------------------------------------------//
        titleCountPlayer.setBounds(getWidth()/2-100, 20, 100, 20);
        titleShowIP.setBounds(getWidth()/2-100, 100, 200, 20);
        titleTime.setBounds(getWidth()/2-100, 60, 100, 20);
        inputCountPlayer.setBounds(getWidth()/2, 20, 100, 20);
        inputTime.setBounds(getWidth()/2, 60, 100, 20);
        titleShowStatus.setBounds(getWidth()/2-100, 160, 200, 20);
        runServer.setBounds(getWidth()/2-90, 200, 200, 40);
        //-------------------------[ set about component ]------------------------------------------//
        panel.setBounds(0,0,300,300);
        panel.setLayout(null);
        panel.setBackground(new Color(0,0,0));
        titleTime.setForeground(new Color(255,255,255));
        titleShowIP.setForeground(new Color(255,255,255));
        titleShowIP.setFont(new Font("Tahoma",Font.BOLD,12));
        titleShowStatus.setForeground(new Color(255,255,255));
        titleShowStatus.setFont(new Font("Tahoma",Font.BOLD,16));
        titleCountPlayer.setForeground(new Color(255,255,255));
        runServer.setCursor(new Cursor(JFrame.HAND_CURSOR));
        runServer.setFocusPainted(false);
        runServer.setForeground(new Color(255,255,255));
        runServer.setBackground(new Color(19,142,0));
        //-------------------------[ buttom Action]------------------------------------------//
        runServer.addActionListener(e->{
            if(!this.baseServer.createServer){
                this.baseServer.createServer = true;
                try{
                    int countPlayer = Integer.parseInt(inputCountPlayer.getText());
                    int countTime = Integer.parseInt(inputTime.getText());
                    //-------------------------[ Run CMD "ipconfig" ]------------------------------------------//
                    try{
                        Process process = Runtime.getRuntime().exec("ipconfig");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        boolean wifiSection = false;
                        while((line = reader.readLine()) != null) {
                            line = line.trim();
                            if(line.startsWith("Wireless LAN adapter Wi-Fi")) {
                                wifiSection = true;
                            }
                            if(wifiSection && line.startsWith("IPv4 Address")) {
                                ip = line.split(":")[1].trim();
                                titleShowIP.setText("Join IP : "+ip);
                                titleShowStatus.setText("สร้างเซิฟเวอร์สำเร็จ");
                                System.out.println(ip);
                                break;
                            }
                        }
                        reader.close();
                    }catch(Exception errCMD){System.out.println(errCMD);}
                    //-------------------------[ Create Server ]------------------------------------------//
                    baseServer.CountPlayerIsReady = countPlayer;
                    baseServer.timeIngame = countTime;
                    Server server = new Server(baseServer);
                    CheckPlayerInServer checkPlayerInServer = new CheckPlayerInServer(baseServer);
                    checkPlayerInServer.start();
                    server.start();
                }catch(NumberFormatException errNumber){}
                runServer.setText("start server");
                runServer.setBackground(new Color(165,42,42));
            }else{
                this.baseServer.createServer = false;
                titleShowIP.setText("");
                titleShowStatus.setText("หยุดทำงานเซิฟเวอร์");
                runServer.setText("stop server");
                runServer.setBackground(new Color(19,142,0));
            }
        });
        //-------------------------[ add component]------------------------------------------//
        panel.add(titleCountPlayer);
        panel.add(inputCountPlayer);
        panel.add(titleTime);
        panel.add(inputTime);
        panel.add(titleShowIP);
        panel.add(titleShowStatus);
        panel.add(runServer);
        add(panel);
    }
}


