class Run {
    public static void main(String[] args){
        BaseClient baseClient = new BaseClient();
        App app = new App("The Meat",baseClient);
        ControlClient controlClient = new ControlClient(app);
        PageMenu menu = new PageMenu(app);
        PageLobby lobby = new PageLobby(app);
        PageStart start = new PageStart(app);
        PageSeting seting = new PageSeting(app);
        PageAbout about = new PageAbout(app);
        app.addPanel(menu,"menu");
        app.addPanel(lobby,"lobby");
        app.addPanel(start,"start");
        app.addPanel(seting, "seting");
        app.addPanel(about, "about");
        app.showPanel("menu");
        controlClient.start();
    }    
}