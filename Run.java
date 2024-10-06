class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageMenu menu = new PageMenu();
        PageStart start = new PageStart(app);
        Sound sound = new Sound();
        app.addPanel(menu,"menu");
        app.addPanel(start,"start");
        app.showPanel("start");
        
    }    
}