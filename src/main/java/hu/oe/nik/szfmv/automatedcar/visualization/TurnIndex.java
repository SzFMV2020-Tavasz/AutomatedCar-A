package hu.oe.nik.szfmv.automatedcar.visualization;

import javax.swing.*;
import java.awt.*;

public class TurnIndex extends JPanel {

    private static final Color signalOFF = Color.white;
    private static final Color signalON = Color.green;
    private static final Color backgroundColor = new Color(0x888888);
    private int[] x;
    private int[] y;
    private Color currentColor = signalOFF;
    private boolean isOn;


    public TurnIndex(int xPos, int yPos, boolean isLeft) {
        setBounds(xPos, yPos, 40, 40);
        if (isLeft) {
            x = new int[]{0, 15, 15, 30, 30, 15, 15};
            y = new int[]{15, 0, 10, 10, 20, 20, 30};
        } else {
            x = new int[]{0, 15, 15, 30, 15, 15, 0};
            y = new int[]{10, 10, 0, 15, 30, 20, 20};
        }
    }

    public void setOn(boolean on) {
        isOn = on;
        if (isOn) {
            currentColor = signalON;
        } else {
            currentColor = signalOFF;
        }
        repaint();
    }

    public void changeSignal() {
        if (currentColor == signalOFF) {
            currentColor = signalON;
        } else {
            currentColor = signalOFF;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 40, 40);
        g.setColor(currentColor);
        g.fillPolygon(x, y, 7);
    }
}
