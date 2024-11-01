import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class PageLobby extends JPanel {
    PlayerPanel showPlayer;
    App app;
    PageLobby(App app) {
        this.app = app;
        setLayout(null);

        JButton back = Component.createCustomRoundedButton("Back",Color.white);
        back.setBounds(0, 0, 100, 60);
        back.addActionListener(e -> {
            this.app.getBaseClient().statusConnectServer = false;
            this.app.getBaseClient().statusReady = false;
            this.app.getBaseClient().nowPage = "menu";
        });
        add(back);
        JButton ready = Component.createCustomRoundedButton("พร้อม",Color.white);
        ready.setFont(new Font("Tahoma", Font.BOLD, 14));
        ready.setBounds(600, 600, 150, 60);
        ready.addActionListener(e -> {
            if(app.getBaseClient().statusReady){
                ready.setText("ยังไม่พร้อม");
                app.getBaseClient().statusReady = false;
            }else{
                ready.setText("พร้อม");
                app.getBaseClient().statusReady = true;
            }
        });
        add(ready);
       ////////////////////////////////// Player Panel ///////////////////////////////
        showPlayer = new PlayerPanel(app);
        showPlayer.setBounds(400, 200, 500, 400);
        add(showPlayer);
    }
    void runSetPlayer(){
        this.showPlayer.startUpdatingPlayers();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon("./image/lobby.png");
        g.drawImage(bg.getImage(), 0, 0, this);
    }
}

class PlayerPanel extends JPanel {
    private final JPanel playerListPanel;
    private final App app;
    public PlayerPanel(App app) {
        this.app = app;
        this.setLayout(new BorderLayout());
        playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(playerListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void startUpdatingPlayers() {
        new Thread(() -> {
            while (this.app.baseServer.statusInRoby) {
                SwingUtilities.invokeLater(this::updatePlayerList);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updatePlayerList() {
        if(this.app.baseServer!=null){
            playerListPanel.removeAll();
            ArrayList<HashMap<String, String>> players = this.app.baseServer.getPlayerInRobby();
            for (HashMap<String, String> playerData : players) {
                String playerName = playerData.get("name");
                String playerStatus = playerData.get("status");
                JLabel playerLabel = new JLabel(playerName + " - " + playerStatus);
                playerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                playerListPanel.add(playerLabel);
            }
            playerListPanel.revalidate();
            playerListPanel.repaint();
        }

    }
}
