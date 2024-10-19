import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderExample extends JFrame {

    public CustomSliderExample() {
        // Create a JSlider
        JSlider slider = new JSlider(0, 100);
        
        // Set custom UI for the slider
        slider.setUI(new CustomSliderUI(slider));
        
        // Adjust slider properties
        slider.setValue(50); // Set initial value to the middle
        slider.setPaintTicks(false); // Hide ticks
        
        add(slider);

        // Set frame properties
        setTitle("Custom JSlider Example");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
        
        // Draw track as a blue rectangle
        g2d.fillRect(trackRect.x, trackRect.y + trackRect.height / 4, trackRect.width, trackRect.height / 2);
    }

    @Override
    public void paintThumb(Graphics g) {
        // Custom thumb color and shape
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE); // Blue color for the thumb
        
        // Draw thumb as a circular shape
        int thumbRadius = 30; // Size of the thumb
        int thumbX = thumbRect.x + thumbRect.width / 2 - thumbRadius / 2;
        int thumbY = thumbRect.y + thumbRect.height / 2 - thumbRadius / 2;
        g2d.fillOval(thumbX, thumbY, thumbRadius, thumbRadius);
    }
}
