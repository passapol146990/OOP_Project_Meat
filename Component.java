import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;

public class Component{
    // Method to create a rounded button with custom background color
    public static JButton createCustomRoundedButton(String text, Color color) {
        return new CustomRoundedButton(text, color);
    }

    // Method to create a custom JSlider
    public static JSlider createCustomSlider() {
        JSlider slider = new JSlider(0, 100); // Slider with min 0 and max 100
        slider.setUI(new CustomSliderUI(slider)); // Apply custom UI to slider
        slider.setValue(50); // Set initial value to 50
        slider.setPaintTicks(false); // No ticks on slider
        return slider;
    }

    // Custom rounded button class
    static class CustomRoundedButton extends JButton {

        public CustomRoundedButton(String text, Color color) {
            super(text);
            setContentAreaFilled(false); // Disable default button painting
            setFocusPainted(false); // Disable focus painting
            setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Remove border
            setBackground(color); // Set background color
            setCursor(new Cursor(JFrame.HAND_CURSOR)); // Set hand cursor
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // If button is pressed, change color
            if (getModel().isArmed()) {
                g2.setColor(new Color(255, 192, 203)); // Change color when pressed
            } else {
                g2.setColor(getBackground()); // Use background color otherwise
            }
            // Draw rounded rectangle (rounded corners)
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            // Draw border
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            g2.dispose(); // Dispose graphics context to avoid memory leaks
            super.paintComponent(g); // Paint text and other default components
        }

        @Override
        public void updateUI() {
            setUI(new BasicButtonUI()); // Use BasicButtonUI for default behavior
        }
    }

    // Custom UI class for JSlider
    static class CustomSliderUI extends BasicSliderUI {

        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintTrack(Graphics g) {
            // Custom track color
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white); // Blue color for the track
            g2d.fillRect(trackRect.x - 10, trackRect.y + trackRect.height - 50, trackRect.width + 20,200);
        }

        @Override
        public void paintThumb(Graphics g) {
            // Custom thumb color and shape
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(27,29,91)); // Blue color for the thumb
            int thumbRadius = 60; // Size of the thumb
            int thumbX = thumbRect.x + thumbRect.width / 2 - thumbRadius / 2;
            int thumbY = thumbRect.y + thumbRect.height / 2 - thumbRadius / 2;
            g2d.fillOval(thumbX, thumbY, thumbRadius, thumbRadius); // Draw a circular thumb
        }
    }
}
