package hu.oe.nik.szfmv.automatedcar.visualization;

import javax.swing.*;
import java.awt.*;

public class OMeter extends JPanel {
    int perfPercentage;
    Point position;
    Point size;
    Color Background = new Color(0x888888);

    public void setPerfPercentage(int perfPercentage) {
        this.perfPercentage = perfPercentage;
        repaint();
    }

    public void setSize(Point size) {
        this.size = size;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Background);
        g.fillRect(0, 0, 110, 110);

        g.drawOval(position.x, position.y, size.x, size.y);
        g.setColor(Color.WHITE);
        g.fillOval(position.x, position.y, size.x, size.y);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 10));

        int x;
        int y;
        x = (int) (size.x / 2 * Math.cos(Math.toRadians(perfPercentage * 1.8)));
        y = (int) (size.y / 2 * Math.sin(Math.toRadians(perfPercentage * 1.8)));
        x *= -1;
        g.setColor(Color.RED);
        g.drawLine(size.x / 2 + x, size.y / 2 - y, size.x / 2, size.y / 2);
    }
}
