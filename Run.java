class Run {
    public static void main(String[] args){
        App app = new App("The Meat");
        PageMenu menu = new PageMenu();
        app.addPanel(menu,"menu");
        app.showPanel("menu");
    }    
}