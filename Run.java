class Run {
    public static void main(String[] args){
        BaseClient baseClient = new BaseClient();
        App app = new App("The Meat",baseClient);
        PageMenu menu = new PageMenu(app);
        PageStart start = new PageStart(app);
        PageSeting seting = new PageSeting(app);
        PageAbout about = new PageAbout(app);
        app.addPanel(menu,"menu");
        app.addPanel(start,"start");
        app.addPanel(seting, "seting");
        app.addPanel(about, "about");
        app.showPanel("start");
        // app.showPanel("menu");
        // app.showPanel("seting");
        //sound.playmusic();
    }    
}