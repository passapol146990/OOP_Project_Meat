import java.util.ArrayList;
import java.util.HashMap;

public class BaseServer {
    int port = 3333;
    private int time;
    private HashMap<String,BaseClient> user = new HashMap<String,BaseClient>();
    private boolean statusStartGame = false;
    private boolean statusInRoby = true;
    private boolean statusInGame = false;
    void setStatusInRoby(Boolean status){
        if(status){
            this.statusInGame = false;
        }
        this.statusInRoby = status;
    }
    void setStatusInGame(Boolean status){
        if(status){
            this.statusInRoby = false;
        }
        this.statusInGame = status;
    }
    Boolean getStatusInRoby(){return this.statusInRoby;}
    Boolean getStatusInGame(){return this.statusInGame;}
//    Set/Get Time Server
    void setTime(int time){this.time=time;}
    int getTime(){return this.time;}
//    Set/Get User
    void setUser(BaseClient baseClient, String ip){
        this.user.put(ip,baseClient);
    }
    HashMap<String,BaseClient> getUser(){
        return user;
    }
//    Set/Get Status Start Game Server
    // void StartStatusGame(){
    //     if(this.statusStartGame){
    //         // System.out.println("เกมเริ่มไปแล้ว");
    //     }else{
    //         // System.out.println("เกมยังไม่เริ่ม");
    //         int status_x = 0;
    //         for (String i : this.user.keySet()){
    //             if(this.user.get(i).getStatusPlayGame()){
    //                 status_x +=1;
    //             }
    //         }
    //         if(status_x==this.user.size()){
    //             this.statusStartGame = true;
    //             this.time = 2;
    //             Countdown cd = new Countdown(this);
    //             cd.start();
    //             for (String ip : this.user.keySet()){
    //                 ServerStartGamer userServerStart = new ServerStartGamer(this,ip,this.user.get(ip));
    //                 userServerStart.start();
    //             }
    //         }
    //     }
    // }
    void setStatusStartGame(boolean status){this.statusStartGame = status;}
    boolean getStatusStartGame(){return this.statusStartGame;}
}