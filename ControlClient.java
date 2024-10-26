public class ControlClient extends Thread{
    private App app;
    ControlClient(App app){
        this.app = app;
    }
    public void run(){
        while(true){
            if(this.app.getBaseClient().statusConnectServer){
                this.app.showPanel("lobby");
            }else{
                this.app.showPanel("menu");
            }
        }
    }
}
