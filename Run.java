class Run {
    public static void main(String[] args){
        BaseClient baseClient = new BaseClient();
        FileHandler file = new FileHandler();
        Sound sound = new Sound(file);
        App app = new App("The Meat",baseClient,sound,file);
        ControlClient controlClient = new ControlClient(app);
        OpenPortClient openPortClient = new OpenPortClient(app, baseClient, 4444);
        PageMenu menu = new PageMenu(app);
        PageStart start = new PageStart(app);
        PageAbout about = new PageAbout(app);
        PageSeting seting = new PageSeting(app);
        PageLobby lobby = new PageLobby(app);
        PageShowscore showscore = new PageShowscore(app);
        app.addPanel(menu,"menu");
        app.addPanel(start,"start");
        app.addPanel(seting, "seting");
        app.addPanel(about, "about");
        app.addPanel(lobby, "lobby");
        app.addPanel(showscore,"showscore");
        openPortClient.start();
        controlClient.start();
    }    
}