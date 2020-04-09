package hu.oe.nik.szfmv.automatedcar.visualization;

import javax.swing.*;
import java.awt.*;

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
        font = new Font(Font.MONOSPACED, Font.BOLD, width / text.length());
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
        g.drawString(text, width / 4, (2 * height / 3));
    }
}
