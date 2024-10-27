import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseClient implements Serializable{
    private static final long serialVersionUID = 1L; // หรือใส่ค่าที่คุณต้องการ
    String id = "";
    private int money = 50;
    private Meat meat = null;
    private int time = 0;
    boolean statusConnectServer = false;
    boolean statusReady = false;
    private String nameShop;
    private ArrayList<HashMap<String,String>> orders = new ArrayList<>();
    private HashMap<String,String> Ordering;
    BaseClient(){
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_!$#?{}()";
        for(int i=0;i<55;i++){
            this.id += string.split("")[new Random().nextInt(0,string.length())];
        }
    }
    void sendOrder(){
        float give = 0;
        if(this.Ordering.get("typeMeat")==this.meat.gettype_meat()){
            give += Integer.parseInt(this.Ordering.get("price"))*0.6;
        }
        if(1==0){}
        // ลบออเดอร์ออก เพื่อที่จะให้เซิฟเวอร์ส่งออเดอร์ใหม่มาให้
        this.orders.remove(Integer.parseInt(this.Ordering.get("index")));
    }
    boolean checkOrdering(){
        boolean status = false;
        if(this.Ordering!=null){status=true;}
        return status;
    }
    HashMap<String,String> getOrdering(){
        return this.Ordering;
    }
    void setOrdering(HashMap<String,String> isorder,int index){
        if(this.Ordering==null){
            this.Ordering = isorder;
            this.Ordering.put("index", String.valueOf(index));
        }
    }
    void addOrder(HashMap<String,String> order){
        this.orders.add(order);
    }
    void setOrder(ArrayList<HashMap<String,String>> order){
        this.orders = order;
    }
    ArrayList<HashMap<String,String>> getOrder(){
        return this.orders;
    }
    void newMeat(String type,int price){
        String typeMeat = "";
        if(this.money>=price){
            this.money -= price;
            if(type=="เนื้อวัว"){
                typeMeat = "01";
            }
            else if(type=="เนื้อวากิว"){
                typeMeat = "02";
            }
            else if(type=="เนื้อสันกลาง"){
                typeMeat = "03";
            }
            if(this.meat==null){
                this.meat = new Meat(typeMeat);
                this.meat.start();
            }else{
                // ป้องการ thead ซ้อมบี้
                this.meat.kill();
                this.meat = new Meat(typeMeat);
                this.meat.start();
            }
        }
    }
    Meat getMeat(){return this.meat;}
    int getMoney(){return this.money;}
    void addMoney(int money){this.money += money;}
    void delMoney(int money){this.money -= money;}
    int getTime(){return this.time;}
    String getFormatTime() {
        int minutes = this.time / 60;
        int seconds = this.time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    void setTime(int time){this.time = time;}
    String getNameShop(){return this.nameShop;}
    void setNameShop(String name ){this.nameShop = name;}
}
class Meat extends Thread implements Serializable{
    private int temp = 0;
    private int meat_left = 0;
    private int meat_rigth = 0;
    private boolean meat_on = true;
    private String type_meat = "01";
    private String rank_meat = "rare";
    private int sted_meat = 1;
    private String image_meat = "./image/meat/"+this.type_meat+"/"+this.rank_meat+this.sted_meat+".png";
    private String id;
    boolean clickMeat = false;//เอาไว้ตรวจสอบว่ากดพลิกเนื้อหรือยังป้องกันมันบั๊ค

    Meat(String type){
        this.type_meat = type;
    }

    public void run(){
        while (this.meat_on){
            if(this.sted_meat==1){
                this.meat_left += new Random().nextInt(1,5);
                if(this.meat_rigth>200*50){
                    this.rank_meat = "over_cook";
                }else if(this.meat_rigth>130*50){
                    this.rank_meat = "medium_well";
                }else if(this.meat_rigth>70*50){
                    this.rank_meat = "medium_rare";
                }else{
                    this.rank_meat = "rare";
                }
            }else{
                this.meat_rigth += new Random().nextInt(1,5);
                if(this.meat_left>200*50){
                    this.rank_meat = "over_cook";
                }else if(this.meat_left>130*50){
                    this.rank_meat = "medium_well";
                }else if(this.meat_left>70*50){
                    this.rank_meat = "medium_rare";
                }else{
                    this.rank_meat = "rare";
                }
            }
            this.temp = (this.meat_left+this.meat_rigth)/2;
            this.image_meat = "./image/meat/"+this.type_meat+"/"+this.rank_meat+this.sted_meat+".png";
            try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}  
        }
    }
    String getImage(){
        return this.image_meat;
    }
    void kill(){
        this.meat_on = false;
        this.image_meat = null;
    }
    int getSted_meat(){return this.sted_meat;}
    void setSted_meat(int sted){this.sted_meat = sted;}
    //ดึงค่าอุณหภูมิ
    public int getTemperature() {
        return this.temp;
    }
    String gettype_meat(){return this.type_meat;}
    String getid(){return this.id;}
}
class ClickMeat extends Thread implements Serializable{
    private Meat meat;
    ClickMeat(Meat meat){
        this.meat = meat;
    }
    public void run(){
        if(!this.meat.clickMeat){
            if(this.meat.getSted_meat()==1){
                while (this.meat.getSted_meat()<7) {
                    this.meat.setSted_meat(this.meat.getSted_meat()+1);
                    try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
                }
            }else{
                while (this.meat.getSted_meat()>1) {
                    this.meat.setSted_meat(this.meat.getSted_meat()-1);
                    try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        }
    }
}



