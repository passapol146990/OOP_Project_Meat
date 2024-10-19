class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageMenu menu = new PageMenu();
        PageLobby lobby = new PageLobby(app);
        Sound sound = new Sound();
        // app.addPanel(menu,"menu");
        // app.showPanel("menu");
        app.addPanel(lobby, "lobby");
        app.showPanel("lobby");
        
    }    
}