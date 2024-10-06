class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageMenu menu = new PageMenu();
        PageSeting seting = new PageSeting();
        Sound sound = new Sound();
        app.addPanel(menu,"menu");
        app.showPanel("menu");
        app.addPanel(seting, "seting");
        app.showPanel("seting");
    }    
}