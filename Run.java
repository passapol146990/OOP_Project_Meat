class Run {
    public static void main(String[] args){
        BaseClient baseClient = new BaseClient();
        App app = new App("The Meat",baseClient);
        PageMenu menu = new PageMenu();
        PageStart start = new PageStart(app);
        Sound sound = new Sound();
        PageSeting seting = new PageSeting(sound);
        app.addPanel(menu,"menu");
        app.addPanel(start,"start");
        app.showPanel("start");
        // app.showPanel("menu");
        app.addPanel(seting, "seting");
        app.showPanel("seting");
        sound.playmusic();
    }    
}