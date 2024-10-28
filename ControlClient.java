public class ControlClient extends Thread{
    private App app;
    ControlClient(App app){
        this.app = app;
    }
    public void run(){
        while(true){
            this.app.showPanel(this.app.getBaseClient().nowPage);
        }
    }
}
