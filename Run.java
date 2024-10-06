class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageAbout about = new PageAbout(app);
        PageMenu menu = new PageMenu();
        Sound sound = new Sound();
        app.addPanel(menu, "menu");
        app.addPanel(about,"about");
        app.showPanel("about");
        
    }    
}