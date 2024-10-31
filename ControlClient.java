public class ControlClient extends Thread{
    private App app;
    ControlClient(App app){
        this.app = app;
    }
    public void run(){
        while(true){
            if(!this.app.getBaseClient().statusConnectServer){
                this.app.getBaseClient().reSetBaseClient();
                this.app.getSound().stopGiveMoney();
                this.app.getSound().closeEffect();
            }else{
                if(this.app.getBaseClient().nowPage=="lobby"){
                    ((PageLobby)this.app.getPanel("lobby")).runSetPlayer();
                    this.app.getBaseClient().reSetBaseClient();
                    this.app.getSound().stopGiveMoney();
                    this.app.getSound().closeEffect();
                }
                if(this.app.getBaseClient().nowPage=="menu"){
                    this.app.getBaseClient().reSetBaseClient();
                    this.app.getSound().stopGiveMoney();
                    this.app.getSound().closeEffect();
                }
            }
            this.app.showPanel(this.app.getBaseClient().nowPage);
            try{Thread.sleep(100);}catch(InterruptedException e){e.printStackTrace();}
        }
    }
}
