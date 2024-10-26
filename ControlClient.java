public class ControlClient extends Thread{
    private App app;
    ControlClient(App app){
        this.app = app;
    }
    public void run(){
        while(true){
            if(this.app.getBaseClient().statusConnectServer){
                if(this.app.baseServer!=null){
                    if(this.app.baseServer.getStatusInGame()){
                        this.app.getBaseClient().setTime(this.app.baseServer.time);
                        this.app.showPanel("start");
                    }else{
                        this.app.showPanel("lobby");
                    }
                }else{
                    this.app.showPanel("lobby");
                }
            }else{
                this.app.showPanel("menu");
            }
        }
    }
}
