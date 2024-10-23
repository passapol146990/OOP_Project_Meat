import java.util.Random;

public class BaseClient{
    private int money = 50;
    private Meat meat=null;
    private int time = 0;
    boolean statusCountTime = false;
    
    BaseClient(){}
    void newMeat(String type){
        if(this.meat==null){
            this.meat = new Meat(type);
            this.meat.start();
        }else{
            // ป้องการ thead ซ้อมบี้
            this.meat.kill();
            this.meat = new Meat(type);
            this.meat.start();
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
    void runStartGame(){
        CountTime runTime = new CountTime(this);
        runTime.start();
    }
}

class Meat extends Thread{
    private int temp=0;
    private int meat_left = 0;
    private int meat_rigth = 0;
    private boolean meat_on = true;
    private String type_meat = "01";
    private String rank_meat = "rare";
    private int sted_meat = 1;
    private String image_meat = "./image/meat/"+this.type_meat+"/"+this.rank_meat+this.sted_meat+".png";
    boolean clickMeat = false;//เอาไว้ตรวจสอบว่ากดพลิกเนื้อหรือยังป้องกันมันบั๊ค
    Meat(String type){
        if(type=="เนื้อวัว"){
            this.type_meat = "01";
        }
        else if(type=="เนื้อวากิว"){
            this.type_meat = "02";
        }
        else if(type=="เนื้อสันกลาง"){
            this.type_meat = "03";
        }else{
            System.out.println("ไม่มีเนื้อ");
        }
    }

    public void run(){
        while (this.meat_on){
            if(this.sted_meat==1){
                this.meat_left += new Random().nextInt(1,5);
                if(this.meat_rigth>200*100){
                    this.rank_meat = "over_cook";
                }else if(this.meat_rigth>130*100){
                    this.rank_meat = "medium_well";
                }else if(this.meat_rigth>70*100){
                    this.rank_meat = "medium_rare";
                }else{
                    this.rank_meat = "rare";
                }
            }else{
                this.meat_rigth += new Random().nextInt(1,5);
                if(this.meat_left>200*100){
                    this.rank_meat = "over_cook";
                }else if(this.meat_left>130*100){
                    this.rank_meat = "medium_well";
                }else if(this.meat_left>70*100){
                    this.rank_meat = "medium_rare";
                }else{
                    this.rank_meat = "rare";
                }
            }
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
}
class ClickMeat extends Thread{
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