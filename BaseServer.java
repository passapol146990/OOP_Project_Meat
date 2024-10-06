import java.util.HashMap;

public class BaseServer {
    private float time;
    private HashMap<String,BaseClient> user = new HashMap<String,BaseClient>();
//    Set/Get Time Server
    void setTime(float time){this.time=time;}
    float getTime(){return this.time;}
//    Set/Get User
    void setUser(BaseClient baseClient, String ip){
        this.user.put(ip,baseClient);
    }
    HashMap<String,BaseClient> getUser(){
        return user;
    }
}
