import java.util.HashMap;

public class BaseServer {
    private float time;
    private HashMap<String,BaseClient> user = new HashMap<String,BaseClient>();
    private boolean statusStartGame = false;
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
//    Set/Get Status Start Game Server
    void StartStatusGame(){
        int status_x = 0;
        for (String i : this.user.keySet()){
            if(this.user.get(i).getStatusPlayGame()){
                status_x +=1;
            }
        }
        if(status_x==this.user.size()){
            this.statusStartGame = true;
            this.time = 50000;
            Countdown cd = new Countdown(this);
            cd.start();
            for (String ip : this.user.keySet()){
                ServerStartGamer userServerStart = new ServerStartGamer(this,ip,this.user.get(ip));
                userServerStart.start();
            }
        }
    }
    void setStatusStartGame(boolean status){this.statusStartGame = status;}
    boolean getStatusStartGame(){return this.statusStartGame;}

}
