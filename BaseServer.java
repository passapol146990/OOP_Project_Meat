import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Random;

public class BaseServer implements Serializable{
    private static final long serialVersionUID = 1L;
    int port = 3333;
    int time = 0;
    int timeIngame = 0;
    int timeStop = 0;
    HashMap<String,BaseClient> client = new HashMap<String,BaseClient>();
    HashMap<String,Boolean> controller_client = new HashMap<String,Boolean>();
    HashMap<String,String> IDClientGETIPAddress = new HashMap<String,String>();
    boolean statusInRoby = true;
    boolean statusInGame = false;
    boolean statusEndGame = false;
    int CountPlayerOnServer = 0;
    int CountPlayerIsReady = 0;
    ArrayList<HashMap<String,String>> orders = new ArrayList<>();
    BaseServer(){
        for(int i=0;i<20;i++){
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(40,50)), "01",140+i));
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(50,70)), "02",140+i));
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(45,65)), "03",140+i));
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(40,50)), "01",210+i));
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(50,70)), "02",210+i));
            this.orders.add(getOrderFormat(String.format("%s", new Random().nextInt(45,65)), "03",201+i));
        }
    }
    HashMap<String,String> getOrderFormat(String price,String typeMeat,int tempMeat){
        HashMap<String,String> order = new HashMap<String,String>();
        String titleFormat = "";
        String image = "./image/meat/"+typeMeat+"/";
        String image_rigth = "./image/meat/"+typeMeat+"/";
        /////////////////////////////////////////
        if(typeMeat=="01"){
            titleFormat += "เนื้อวัว ";
        }else if(typeMeat=="02"){
            titleFormat += "เนื้อวากิล ";
        }else if(typeMeat=="03"){
            titleFormat += "เนื้อสันกลาง ";
        }
        /////////////////////////////////////////
        if(tempMeat>200){
            titleFormat += "แบบมีเดียมเวล ";
            image+="medium_well1.png";
            image_rigth+="medium_well7.png";
        }else if(tempMeat>130){
            titleFormat += "แบบมีเดียมแรร์ ";
            image+="medium_rare1.png";
            image_rigth+="medium_rare7.png";
        }
        /////////////////////////////////////////
        titleFormat += "อุณหภูมิ "+String.valueOf(tempMeat)+" °C";

        /////////////////////////////////////////
        order.put("image", image);
        order.put("image_rigth", image_rigth);
        order.put("title", titleFormat);
        order.put("price", price);
        order.put("typeMeat", typeMeat);
        order.put("tempMeat", String.valueOf(tempMeat));
        return order;
    }
    HashMap<String,String> getRandomOrder(){
        return orders.get(new Random().nextInt(0,orders.size()));
    }
    Boolean getStatusInRoby(){return this.statusInRoby;}
    Boolean getStatusInGame(){return this.statusInGame;}
    Boolean getStatusEndGame(){return this.statusEndGame;}

    void setClient(BaseClient baseClient, String ip){
        this.client.put(ip,baseClient);
    }
    BaseClient getClientByID(String ID){
        return client.get(IDClientGETIPAddress.get(ID));
    }
    ArrayList<HashMap<String,String>> getPlayerInRobby(){
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        for(String key : this.client.keySet()){
            if(this.controller_client.get(key)){
                HashMap<String,String> player = new HashMap<String,String>();
                player.put("name",this.client.get(key).getNameShop());
                String status = "ยังไม่พร้อม";
                if(this.client.get(key).statusReady){
                    status = "พร้อม";
                }
                player.put("status",status);
                data.add(player);
            }
        }
        return data;
    }
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
                this.statusEndGame = false;
                this.statusInGame = true;
                this.time = this.timeIngame;
                CountTimeServer countime = new CountTimeServer(this);
                countime.start(); 
            }
        }
    }
    void checkDataBasePlayerInGame(){
        if(this.statusInGame){
            if(this.time<=this.timeStop){
                this.statusEndGame = true;
                this.statusInGame = false;
                this.statusInRoby = false;
            }
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
class CountTimeServer extends Thread implements Serializable{
    private static final long serialVersionUID = 1L;
    private BaseServer base;
    CountTimeServer(BaseServer base){
        this.base = base;
    }
    public void run(){
        while (this.base.getStatusInGame()) {
            this.base.time = this.base.time-1;
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        }
        this.base.statusEndGame = true;
        this.base.statusInGame = false;
        this.base.statusInRoby = false;
    }
    
}
