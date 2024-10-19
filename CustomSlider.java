import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSlider extends JFrame {

    public CustomSlider() {
        JSlider slider = new JSlider(0, 100);
        
        // Set custom UI for the slider
        slider.setUI(new CustomSliderUI(slider));
        
        // Adjust slider properties
        slider.setValue(50); // Set initial value
        slider.setPaintTicks(false); // Hide ticks
        
        add(slider);

        setTitle("Custom JSlider");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomSlider slider = new CustomSlider();
            slider.setVisible(true);
        });
    }
}

// Custom UI class for JSlider
class CustomSliderUI extends BasicSliderUI {

    public CustomSliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paintTrack(Graphics g) {
        // Custom track color
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE); // Blue color for the track
        
        // Draw track
        g2d.fillRect(trackRect.x, trackRect.y + trackRect.height / 4, trackRect.width, trackRect.height / 2);
    }

    @Override
    public void paintThumb(Graphics g) {
        // Custom thumb color and shape
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE); // Blue color for the thumb
        
        // Draw a circular thumb
        int thumbRadius = 12;
        g2d.fillOval(thumbRect.x, thumbRect.y, thumbRadius, thumbRadius);
    }
}
