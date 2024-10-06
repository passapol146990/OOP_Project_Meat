import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * InnerClient
 */
public class RunClient {
    public static void main(String[] args){
        String ipaddress = "127.0.0.1";
        BaseClient BaseClient = new BaseClient();
        int port = 3333;
        Client client = new Client(ipaddress, port, BaseClient);
        client.start();
        Input input = new Input(BaseClient);
        input.start();
    }
}

class Client extends Thread{
    private String ipaddress;
    private int port;
    private BaseClient client;
    Client(String ipaddress, int port,BaseClient client){
        this.ipaddress = ipaddress;
        this.port = port;
        this.client = client;
    }
    public void run(){
        try{
            while (true) {
                Socket socket = new Socket(this.ipaddress,this.port);
                ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
                send.writeObject(this.client);
                send.flush();
    
                send.close();
                socket.close();
                Thread.sleep(100);
            }
        } catch (Exception e) {}
    }
}
class Input extends Thread{
    private BaseClient baseClient;
    Input(BaseClient baseClient){
        this.baseClient = baseClient;
    }
    public void run(){
        while(true){
            Scanner sc = new Scanner(System.in);
            boolean x = sc.nextBoolean();
            this.baseClient.setStatusPlayGame(x);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    
}
