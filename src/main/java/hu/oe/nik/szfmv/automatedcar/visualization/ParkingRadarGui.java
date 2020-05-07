package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;


/**
 * JPanel element to show the result of the parking radar with graphics
 * The seven segment font is free for personal use (https://www.dafont.com/seven-segment.font)
 */
public class ParkingRadarGui extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static int HEIGHT = 40;

    private static int RED_RECT_WIDTH = 12;
    private static int GREEN_RECT_WIDTH = 6;
    private static int RECT_HEIGHT = 20;
    private static int RECT_TOP = 20; //(HEIGHT - RECT_HEIGHT)/2;
    private static int RECT_SEPARATOR = 3;
    private static int RECT_STARTER = 15;

    private static float[] GREEN_CATEGORIES = new float[]{0.8f, 0.7f, 0.6f, 0.5f};
    private static float RED_CATEGORY = 0.4f;

    private static int FONT_SIZE_DISTANCE = 50;
    private static int FONT_SIZE_METER = 20;


    // this is stupid, magic number or not.
    private static int ZERO = 0;

    int width;
    int x;
    int y;
    int backgroundColor;
    float distanceLeft;
    float distanceRight;
    Font distanceFont;
    Font normalFont;

    public ParkingRadarGui(int x, int y, int width, int backgroundColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.backgroundColor = backgroundColor;
        this.setBounds(x, y, width, HEIGHT);
        distanceLeft = 0;
        distanceRight = 0;
        createFonts();
    }

    public void setDistanceLeft(float distance) {
        this.distanceLeft = distance;
        repaint();
    }

    public void setDistanceRight(float distance) {
        this.distanceRight = distance;
        repaint();
    }

    private void createFonts() {
        normalFont = new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE_METER);

        Map<TextAttribute, Object> fontAttributes = new HashMap<>();
        fontAttributes.put(TextAttribute.SIZE, FONT_SIZE_DISTANCE);
        fontAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);
        Font tempFont;
        try {
            InputStream fontStream = ClassLoader.getSystemResourceAsStream("seven_segment.ttf");
            tempFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            distanceFont = tempFont.deriveFont(fontAttributes);
        } catch (IOException | FontFormatException e) {
            distanceFont = normalFont;
        }
    }

    private void drawRects(Graphics g) {
        // check green
        for (int i = 0; i < GREEN_CATEGORIES.length; i++) {
            if (this.distanceLeft < GREEN_CATEGORIES[i]) {
                drawGreenRect(g, true, i + 1);
            }
            if (this.distanceRight < GREEN_CATEGORIES[i]) {
                drawGreenRect(g, false, i + 1);
            }
        }
        // check red
        if (distanceLeft < RED_CATEGORY) {
            drawRedRect(g, true);
        }
        if (distanceRight < RED_CATEGORY) {
            drawRedRect(g, false);
        }
        labelLeftRight(g);
    }

    private void drawGreenRect(Graphics g, boolean left, int num) {
        if (num > ZERO && num <= GREEN_CATEGORIES.length) {
            int leftstart = RECT_STARTER + (num - 1) * (RECT_SEPARATOR + GREEN_RECT_WIDTH);
            int rightstart = width - RECT_STARTER - GREEN_RECT_WIDTH * num - (num - 1) * RECT_SEPARATOR;
            g.setColor(Color.green);
            if (left) {
                g.fillRect(leftstart, RECT_TOP, GREEN_RECT_WIDTH, RECT_HEIGHT);
            } else {
                g.fillRect(rightstart, RECT_TOP, GREEN_RECT_WIDTH, RECT_HEIGHT);
            }
        }
    }

    private void drawRedRect(Graphics g, boolean left) {
        int greenRectsCount = GREEN_CATEGORIES.length;
        int leftstart = RECT_STARTER + greenRectsCount * (RECT_SEPARATOR + GREEN_RECT_WIDTH);
        int rightstart = width - RECT_STARTER - RED_RECT_WIDTH -
            (greenRectsCount) * (RECT_SEPARATOR + GREEN_RECT_WIDTH);
        g.setColor(Color.red);
        if (left) {
            g.fillRect(leftstart, RECT_TOP, RED_RECT_WIDTH, RECT_HEIGHT);
        } else {
            g.fillRect(rightstart, RECT_TOP, RED_RECT_WIDTH, RECT_HEIGHT);
        }
    }

    private void showDistance(Graphics g) {
        String value = String.format("%.1f", min(distanceLeft, distanceRight));
        g.setColor(Color.black);
        g.setFont(distanceFont);
        g.drawString(value, (width - g.getFontMetrics().stringWidth(value)) / 2, HEIGHT);
    }

    private void labelLeftRight(Graphics g) {
        g.setFont(normalFont);
        g.setColor(Color.darkGray);
        g.drawString("L", 0, HEIGHT);
        g.setFont(normalFont);
        g.setColor(Color.darkGray);
        g.drawString("R", width - g.getFontMetrics().stringWidth("R"), HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // mask out the background
        g.setColor(new Color(backgroundColor));
        g.fillRect(0, 0, width, HEIGHT);
        drawRects(g);
        showDistance(g);
    }
}
