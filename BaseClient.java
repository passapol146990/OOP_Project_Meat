import java.util.Random;
import java.io.Serializable;

public class BaseClient implements Serializable{
    private boolean statusConnect = false; // สเตตัสเชื่อมต่อกับ server
    private Boolean statusPlayGame = false; // สเตตัสว่าเรากดพร้อม/ไม่พร้อม
    private float time;
    private int portServerClient;
    // private String id;
   BaseClient(int port){
    this.portServerClient = port;
    //    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //    String x= "";
    //    while (x.length()<20){
    //        x += new Random().nextInt(CHARACTERS.length());
    //    }
    //    // ใช้ SHA-256 ในการทำ hash
    //    String hashedValue = Hasher.hashString(x, "SHA-256");
    //    this.id = hashedValue;
    //    System.out.println("Create ID : "+hashedValue);
   }
   int getPortServerClient(){return this.portServerClient;}
//    Get/Set ID
    // void setId(String id){this.id=id;}
    // String getID(){return this.id;}
//     Get/Set Time
    void setTime(float time){this.time=time;}
    String getTime(){
        int sec = (int)(this.time)/1000;
        int hour = (int)sec/3600;
        sec -=hour * 3600;
        int min = (int)sec/60;
        sec -=min * 60;
        return min+":"+sec;
    }
//    status play game {true, false}
    void setStatusPlayGame(boolean status){this.statusPlayGame=status;}
    boolean getStatusPlayGame(){return this.statusPlayGame;}
//    status Connect Server {true, false}
    void setStatusConnectServer(boolean status){this.statusConnect=status;}
    boolean getStatusConnectServer(){return this.statusConnect;}
}
