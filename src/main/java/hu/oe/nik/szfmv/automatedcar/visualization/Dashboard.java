package hu.oe.nik.szfmv.automatedcar.visualization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(Dashboard.class);

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    private Gui parent;

    private Thread timer = new Thread() {
        int difference;

        public void run() {
            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    LOGGER.error(ex.getMessage());
                }

            }
        }
    };

    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        parent = pt;

        timer.start();
    }

}
