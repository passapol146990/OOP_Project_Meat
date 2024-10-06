import java.util.Random;
import java.io.Serializable;

public class BaseClient implements Serializable{
    private float time;
    // private String id;
    private Boolean statusPlayGame = false;
   BaseClient(){
    //    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //    String x= "";
    //    while (x.length()<20){
    //        x += new Random().nextInt(CHARACTERS.length());
    //    }
    //    // ใช้ SHA-256 ในการทำ hash
    //    String hashedValue = Hasher.hashString(x, "SHA-256");
    //    this.id = hashedValue;
    //    System.out.println("Create ID : "+hashedValue);
   }
//    Get/Set ID
    // void setId(String id){this.id=id;}
    // String getID(){return this.id;}
//     Get/Set Time
    void setTime(float time){this.time=time;}
    float getTime(){return this.time;}
//    status play game {true, false}
    void setStatusPlayGame(boolean status){this.statusPlayGame=status;}
    boolean getStatusPlayGame(){return this.statusPlayGame;}
}
