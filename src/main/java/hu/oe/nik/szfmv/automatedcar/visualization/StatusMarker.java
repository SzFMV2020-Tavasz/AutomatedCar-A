package hu.oe.nik.szfmv.automatedcar.visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class StatusMarker extends JPanel {
    int x;
    int y;
    int width;
    int height;
    String text;
    Font font;
    boolean isItOn = false;
    Color myColor;


    public StatusMarker(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        Map<TextAttribute, Object> fontAttributes = new HashMap<>();
        fontAttributes.put(TextAttribute.TRACKING, -0.05); // to make font characters thighter together
        Font tempFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
        font = tempFont.deriveFont(fontAttributes);
        setBounds(this.x, this.y, this.width, this.height);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public void switchIt(boolean value) {
        isItOn = value;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isItOn) {
            myColor = Color.green;
        } else {
            myColor = Color.white;
        }
        g.setColor(myColor);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(font);
        g.drawString(text, (width - g.getFontMetrics().stringWidth(text)) / 2, (2 * height / 3));
    }
}
