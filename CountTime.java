class CountTime extends Thread{
    private BaseClient baseClient;
    CountTime(BaseClient baseClient){
        this.baseClient = baseClient;
    }
    public void run(){
        if(!this.baseClient.statusCountTime){
            while (this.baseClient.getTime()>0) {
                this.baseClient.setTime(this.baseClient.getTime()-1);
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }
    
}
