import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Component{
    // Method to create a rounded button with custom background color
    public static JButton createCustomRoundedButton(String text, Color color) {
        return new CustomRoundedButton(text, color);
    }

    // Method to create a custom JSlider
    public static JSlider createCustomSlider(int value) {
        JSlider slider = new JSlider(0, 100,value); // Slider with min 0 and max 100
        slider.setUI(new CustomSliderUI(slider)); // Apply custom UI to slider
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
        private boolean isThumbHovered = false;

        @Override
            protected Dimension getThumbSize() {
                // ปรับขนาดปุ่มเลื่อน (thumb)
                return new Dimension(52, 52);
            }
          
        public CustomSliderUI(JSlider slider) {
            super(slider);
            slider.setBackground(Color.blue);
            slider.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (thumbRect.contains(e.getPoint())) {
                        isThumbHovered = true;
                    } else {
                        isThumbHovered = false;
                    }
                    slider.repaint(); // Repaint slider to update hover effect
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    mouseMoved(e);
                }
            });
        }

        @Override
        public void paintTrack(Graphics g) {
            // Custom track color
            Graphics2D g2d = (Graphics2D) g;
            Rectangle trackBounds = trackRect;
            int thumbPos = thumbRect.x + (thumbRect.width / 2);

                g2d.setColor(Color.white);
                g2d.fillRect(thumbPos, trackBounds.y + (trackBounds.height / 2) - 2, trackBounds.width - thumbPos, 4);

                g2d.setColor(Color.blue);
                g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2,  thumbPos, 4);
        }

        @Override
        public void paintThumb(Graphics g) {
            // Custom thumb color and shape
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(27,29,91)); // Blue color for the thumb
            int thumbRadius = 48; // Size of the thumb
            int thumbX = thumbRect.x + thumbRect.width / 2 - thumbRadius / 2;
            int thumbY = thumbRect.y + thumbRect.height / 2 - thumbRadius / 2;
            g2d.fillOval(thumbX, thumbY, thumbRadius, thumbRadius); // Draw a circular thumb

            if (isThumbHovered) {
                g2d.setColor(Color.GRAY); // Gray color for hover border
                g2d.setStroke(new BasicStroke(3)); // Set the thickness of the border
                g2d.drawOval(thumbX, thumbY, thumbRadius, thumbRadius); // Draw a border around the thumb
            }
        }
    }
}
