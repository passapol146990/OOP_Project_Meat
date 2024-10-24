import java.util.ArrayList;
import java.util.HashMap;

public class BaseServer {
    int port = 3333;
    private int time;
    private HashMap<String,BaseClient> user = new HashMap<String,BaseClient>();
    HashMap<String,Boolean> controller_client = new HashMap<String,Boolean>();
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
    void setStatusStartGame(boolean status){this.statusStartGame = status;}
    boolean getStatusStartGame(){return this.statusStartGame;}
}