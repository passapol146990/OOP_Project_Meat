class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageMenu menu = new PageMenu();
        Sound sound = new Sound();
        PageSeting seting = new PageSeting(sound);
        app.addPanel(menu,"menu");
        app.showPanel("menu");
        app.addPanel(seting, "seting");
        app.showPanel("seting");
        //sound.playmusic();
    }    
}