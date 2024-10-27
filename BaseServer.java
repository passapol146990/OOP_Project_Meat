import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Random;

public class BaseServer implements Serializable{
    private static final long serialVersionUID = 1L; // หรือใส่ค่าที่คุณต้องการ
    int port = 3333;
    int time = 300;
    private HashMap<String,BaseClient> client = new HashMap<String,BaseClient>();
    HashMap<String,Boolean> controller_client = new HashMap<String,Boolean>();
    HashMap<String,String> IDClientGETIPAddress = new HashMap<String,String>();
    private boolean statusStartGame = false;
    private boolean statusInRoby = true;
    private boolean statusInGame = false;
    int CountPlayerOnServer = 0;
    private int CountPlayerIsReady = 1;
    ArrayList<HashMap<String,String>> orders = new ArrayList<>();
    BaseServer(){
        this.orders.add(getOrderFormat("./image/meat/01/medium_rare1.png", "เนื้อวัว แบบมีเดียมแรร์ อุณหภูมิ 130 องศา", "49", "01","130"));
        this.orders.add(getOrderFormat("./image/meat/02/medium_rare1.png", "เนื้อวากิล แบบมีเดียมแรร์ อุณหภูมิ 130 องศา", "60", "02","130"));
        this.orders.add(getOrderFormat("./image/meat/03/medium_rare1.png", "เนื้อสันกลาง แบบมีเดียมแรร์ อุณหภูมิ 130 องศา", "50", "03","130"));
        this.orders.add(getOrderFormat("./image/meat/01/medium_well1.png", "เนื้อวัว แบบมีเดียมเวล อุณหภูมิ 150 องศา", "47", "01","150"));
        this.orders.add(getOrderFormat("./image/meat/02/medium_well1.png", "เนื้อวัว แบบมีเดียมเวล อุณหภูมิ 150 องศา", "58", "02","150"));
        this.orders.add(getOrderFormat("./image/meat/03/medium_well1.png", "เนื้อวัว แบบมีเดียมเวล อุณหภูมิ 150 องศา", "52", "03","150"));
    }
    HashMap<String,String> getOrderFormat(String img,String title,String price,String typeMeat,String tempMeat){
        HashMap<String,String> order = new HashMap<String,String>();
        order.put("image", img);
        order.put("title", title);
        order.put("price", price);
        order.put("typeMeat", typeMeat);
        order.put("tempMeat", tempMeat);
        return order;
    }
    HashMap<String,String> getRandomOrder(){
        return orders.get(new Random().nextInt(0,orders.size()));
    }
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
    BaseClient getClientByID(String ID){
        return client.get(IDClientGETIPAddress.get(ID));
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
    void checkDataBasePlayerInGame(){
        if(this.statusInGame){
            for(String key : this.client.keySet()){
                // ตรวจสอบออเดอร์ของผู้เล่น
                if(this.client.get(key).getOrder().size()<5){
                    this.client.get(key).addOrder(getRandomOrder());
                }
            }
        }
    }
    //เปรียบเทียบค่าเงิน แล้วเก็บไว้ใน arraylist
    ArrayList<BaseClient> getPlayerRankings() {
        ArrayList<BaseClient> onlinePlayers = new ArrayList<>();
    
        // กรองเฉพาะผู้เล่นที่ออนไลน์
        for (String ip : client.keySet()) {
            if (controller_client.getOrDefault(ip, false)) { // ตรวจสอบว่าออนไลน์อยู่
                onlinePlayers.add(client.get(ip));
            }
        }
        // จัดเรียงตามจำนวนเงินจากมากไปน้อย
        onlinePlayers.sort(Comparator.comparingDouble(BaseClient::getMoney).reversed());
        return onlinePlayers;
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
