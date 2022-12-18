package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConversionSettingsDialog extends JDialog {
    private JPanel pnlNorth;
    private JLabel lblMode;
    private String modeOG;
    private String modeConverted;
    private JPanel pnlCenter;
    private JScrollPane scrollPane;
    private JTextArea taDisplay;
    private JPanel pnlSouth;
    private JPanel pnlButtons;
    private JButton btnExport;
    private JButton btnConvert;
    private JButton btnReset;
    private JButton btnCancel;
    private JPanel pnlInnerGrid;
    private JPanel pnlInnerGrid1;
    private JPanel pnlInnerGrid2;
    private JPanel pnlInnerGrid3;
    private JPanel pnlInnerGrid4;
    private JPanel pnlInnerGrid5;
    private JPanel pnlInnerGrid6;
    private JPanel pnlInnerGrid7;
    private JPanel pnlInnerGrid8;
    private JLabel lblBrowser;
    private JComboBox<String> cmbBrowser;
    private JLabel lblIncognito;
    private JCheckBox chckIncognito;
    private JLabel lblDelay;
    private JCheckBox chckDelay;
    private JLabel lblIntervalLength;
    private JTextField tfInterval;

    public ConversionSettingsDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("MassLinkOpener - Settings");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        FlowLayout flowLeft = new FlowLayout();
        flowLeft.setAlignment(FlowLayout.LEFT);
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);

        // NORTH
        pnlNorth = new JPanel();
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        pnlNorth.setLayout(flowLeft);
        this.modeOG = "Original";
        this.modeConverted = "Converted (BAT File)";
        lblMode = new JLabel("Mode: " + modeOG);
        lblMode.setFont(new Font(null, Font.BOLD, 15));
        pnlNorth.add(lblMode);

        // DISPLAY
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(0, 5, 0, 5));
        taDisplay = new JTextArea();
        taDisplay.setText(taDisplayMW.getText());
        taDisplay.setEditable(false);
        taDisplay.setColumns(75);
        taDisplay.setRows(25);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(taDisplay);
        pnlCenter.add(scrollPane);

        // SOUTH
        pnlSouth = new JPanel();
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        pnlSouth.setLayout(new GridLayout(2, 1));
        pnlButtons = new JPanel();
        pnlButtons.setLayout(flowRight);
        btnExport = new JButton("Export");
        btnExport.setEnabled(false);
        btnExport.addActionListener(e -> {
            this.setVisible(false);
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Batch file (.bat)", "bat");
            fc.setFileFilter(filter);
            int retVal = fc.showSaveDialog(mw);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(fc.getSelectedFile().getAbsolutePath() + ".bat");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(this.taDisplay.getText());
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(mw, io.getMessage());
                }
            }
        });
        btnConvert = new JButton("Convert");
        String clipboard = this.taDisplay.getText();
        btnConvert.addActionListener(e -> {
            this.lblMode.setText("Mode: " + modeConverted);
            this.btnExport.setEnabled(true);
            String[] clipboardParts = clipboard.trim().split("\n");
            String browser = "";
            if (this.cmbBrowser.getSelectedIndex() == 0) {
                browser = "chrome ";
            }
            if (this.cmbBrowser.getSelectedIndex() == 1) {
                browser = "brave ";
            }
            if (this.cmbBrowser.getSelectedIndex() == 2) {
                browser = "firefox ";
            }
            String incognito = "";
            if (this.chckIncognito.isSelected()) {
                incognito = "--incognito ";
            }
            int delayLength = Integer.parseInt(tfInterval.getText());
            String delay = "";
            if (this.chckDelay.isSelected()) {
                delay = "TIMEOUT " + delayLength + "\n";
            }
            this.taDisplay.setText("");
            this.taDisplay.append("@echo off\n");
            for (String url : clipboardParts) {
                this.taDisplay.append("start " + browser + incognito + url + "\n" + delay);
            }
            this.taDisplay.append("@end");
        });
        btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> {
            this.lblMode.setText("Mode: " + modeOG);
            this.btnExport.setEnabled(false);
            this.taDisplay.setText(taDisplayMW.getText());
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlButtons.add(btnExport);
        pnlButtons.add(btnConvert);
        pnlButtons.add(btnReset);
        pnlButtons.add(btnCancel);
        pnlInnerGrid = new JPanel();
        pnlInnerGrid1 = new JPanel();
        pnlInnerGrid2 = new JPanel();
        pnlInnerGrid3 = new JPanel();
        pnlInnerGrid4 = new JPanel();
        pnlInnerGrid5 = new JPanel();
        pnlInnerGrid6 = new JPanel();
        pnlInnerGrid7 = new JPanel();
        pnlInnerGrid8 = new JPanel();
        pnlInnerGrid1.setLayout(flowRight);
        pnlInnerGrid2.setLayout(flowLeft);
        pnlInnerGrid3.setLayout(flowRight);
        pnlInnerGrid4.setLayout(flowLeft);
        pnlInnerGrid5.setLayout(flowRight);
        pnlInnerGrid6.setLayout(flowLeft);
        pnlInnerGrid7.setLayout(flowRight);
        pnlInnerGrid8.setLayout(flowLeft);
        pnlInnerGrid.setLayout(new GridLayout(1, 8));
        lblBrowser = new JLabel("Browser: ");
        cmbBrowser = new JComboBox<>();
        cmbBrowser.addItem("Chrome");
        cmbBrowser.addItem("Brave");
        cmbBrowser.addItem("FireFox");
        lblIncognito = new JLabel("Incognito: ");
        chckIncognito = new JCheckBox();
        chckIncognito.setSelected(false);
        lblDelay = new JLabel("Delay: ");
        chckDelay = new JCheckBox();
        chckDelay.setSelected(false);
        lblIntervalLength = new JLabel("Delay interval: ");
        tfInterval = new JTextField();
        tfInterval.setColumns(2);
        tfInterval.setText(Integer.toString(1));
        pnlInnerGrid1.add(lblBrowser);
        pnlInnerGrid2.add(cmbBrowser);
        pnlInnerGrid3.add(lblIncognito);
        pnlInnerGrid4.add(chckIncognito);
        pnlInnerGrid5.add(lblDelay);
        pnlInnerGrid6.add(chckDelay);
        pnlInnerGrid7.add(lblIntervalLength);
        pnlInnerGrid8.add(tfInterval);
        pnlInnerGrid.add(pnlInnerGrid1);
        pnlInnerGrid.add(pnlInnerGrid2);
        pnlInnerGrid.add(pnlInnerGrid3);
        pnlInnerGrid.add(pnlInnerGrid4);
        pnlInnerGrid.add(pnlInnerGrid5);
        pnlInnerGrid.add(pnlInnerGrid6);
        pnlInnerGrid.add(pnlInnerGrid7);
        pnlInnerGrid.add(pnlInnerGrid8);
        pnlSouth.add(pnlInnerGrid);
        pnlSouth.add(pnlButtons);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
