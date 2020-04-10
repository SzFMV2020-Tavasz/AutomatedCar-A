package hu.oe.nik.szfmv.automatedcar.visualization;

import javax.swing.*;
import java.awt.*;

public class DashboardLayout extends JPanel{

    private final GridLayout mainGridLayout = new GridLayout(5,1, 8, 8);
    private final GridLayout meterGridLayout = new GridLayout(1,2,8,8);
    private final GridLayout indexGridLayout = new GridLayout(1,3,8,8);
    private final GridLayout pedalGridLayout = new GridLayout(4,1,8,8);
    private final GridLayout accGridLayout = new GridLayout(4, 2, 4,4);
    private final GridLayout debugGridLayout = new GridLayout(4, 1, 8, 8);

//  private JPanel mainPanel;
    private JPanel meterPanel;
    private JPanel indexPanel;
    private JPanel pedalPanel;
    private JPanel accPanel;
    private JPanel debugPanel;



    public DashboardLayout() {
        meterPanel = new JPanel();
        meterPanel.setLayout(meterGridLayout);
        indexPanel = new JPanel();
        indexPanel.setLayout(indexGridLayout);
        pedalPanel = new JPanel();
        pedalPanel.setLayout(pedalGridLayout);
        accPanel = new JPanel();
        accPanel.setLayout(accGridLayout);
        debugPanel.setLayout(debugGridLayout);


        add(meterPanel);
        add(indexPanel);
        add(accPanel);
        add(pedalPanel);
        add(debugPanel);

    }


}
