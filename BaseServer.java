import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BaseServer implements Serializable{
    int port = 3333;
    int time = 300;
    private HashMap<String,BaseClient> client = new HashMap<String,BaseClient>();
    HashMap<String,Boolean> controller_client = new HashMap<String,Boolean>();
    private boolean statusStartGame = false;
    private boolean statusInRoby = true;
    private boolean statusInGame = false;
    int CountPlayerOnServer = 0;
    private int CountPlayerIsReady = 1;
    void setStatusInRoby(Boolean status){
        if(status){
            this.time = 0;
            this.statusInGame = false;
        }
        this.statusInRoby = status;
    }
    Boolean getStatusInRoby(){return this.statusInRoby;}
    Boolean getStatusInGame(){return this.statusInGame;}
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
    void checkReadyPlayer(){
        //ถ้าผู้เล่นใน server ครบตามจำนวนที่พร้อมเล่นของ server จะทำงาน
        if(this.CountPlayerOnServer>=this.CountPlayerIsReady){
            int ready = 0;
            for(String key : this.client.keySet()){
                if(this.client.get(key).statusReady){
                    ready += 1;
                }
            }
            if(ready>=this.CountPlayerIsReady){
                this.statusInRoby = false;
                this.statusInGame = true;
                this.time = 300;
                CountTimeServer countime = new CountTimeServer(this);
                countime.start(); 
            }
        }

    }
    //เปรียบเทียบค่าเงิน แล้วเก็บไว้ใน arraylist
        ArrayList<BaseClient> getPlayerRankings() {
            return this.client.values().stream()
                    .sorted(Comparator.comparingDouble(BaseClient::getMoney).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    
}
class CountTimeServer extends Thread{
    private BaseServer base;
    CountTimeServer(BaseServer base){
        this.base = base;
    }
    public void run(){
        while (this.base.time>0) {
            this.base.time = this.base.time-1;
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    
}
