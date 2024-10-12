import java.util.Random;
import java.io.Serializable;

public class BaseClient implements Serializable{
    private boolean statusConnect = false; // สเตตัสเชื่อมต่อกับ server
    private Boolean statusPlayGame = false; // สเตตัสว่าเรากดพร้อม/ไม่พร้อม
    private int time;
    private int portServerClient;
   BaseClient(int port){
    this.portServerClient = port;
   }
   int getPortServerClient(){return this.portServerClient;}
//     Get/Set Time
    void setTime(int time){this.time=time;}
    int getTime(){
        // int sec = (int)(this.time)/1000;
        // int hour = (int)sec/3600;
        // sec -=hour * 3600;
        // int min = (int)sec/60;
        // sec -=min * 60;
        // return min+":"+sec;
        return this.time;
    }
//    status play game {true, false}
    void setStatusPlayGame(boolean status){this.statusPlayGame=status;}
    boolean getStatusPlayGame(){return this.statusPlayGame;}
//    status Connect Server {true, false}
    void setStatusConnectServer(boolean status){this.statusConnect=status;}
    boolean getStatusConnectServer(){return this.statusConnect;}
}
