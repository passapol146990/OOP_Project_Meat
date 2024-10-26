import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class BaseServer implements Serializable{
    int port = 3333;
    private int time;
    private HashMap<String,BaseClient> client = new HashMap<String,BaseClient>();
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
    void setClient(BaseClient baseClient, String ip){
        this.client.put(ip,baseClient);
    }
    HashMap<String,BaseClient> getClient(){
        return client;
    }
    ArrayList<HashMap<String,String>> getPlayerInRobby(){
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        for(String key : this.client.keySet()){
            HashMap<String,String> player = new HashMap<String,String>();
            player.put("name",this.client.get(key).getNameShop());
            String status = "ยังไม่พร้อม";
            if(this.client.get(key).statusReady){
                status = "พร้อม";
            }
            player.put("status",status);
            data.add(player);
        }
        return data;
    }
    void setStatusStartGame(boolean status){this.statusStartGame = status;}
    boolean getStatusStartGame(){return this.statusStartGame;}
}