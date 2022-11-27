package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditDialog extends JDialog {
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JScrollPane scrollPane;
    private JTextArea taDisplay;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JComboBox<String> cmbFormatter;
    private JPanel pnlSouthRight;
    private JButton btnSaveChanges;
    private JButton btnClear;
    private JButton btnCancel;

    // TODO: Add formatter button
    public EditDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Editing");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        // HEADER
        pnlNorth = new JPanel();
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlNorth.setLayout(flowCenter);
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTitle = new JLabel("Mass Link Opener: Editing");
        lblTitle.setFont(new Font(null, Font.BOLD, 20));
        pnlNorth.add(lblTitle);

        // DISPLAY
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(0, 5, 0, 5));
        scrollPane = new JScrollPane();
        taDisplay = new JTextArea();
        taDisplay.setColumns(75);
        taDisplay.setRows(25);
        taDisplay.setText(taDisplayMW.getText());
        scrollPane.setViewportView(taDisplay);
        pnlCenter.add(scrollPane);

        // BUTTONS
        pnlSouth = new JPanel();
        pnlSouthLeft = new JPanel();
        pnlSouthRight = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(new BorderLayout());
        pnlSouthLeft.setLayout(new FlowLayout());
        pnlSouthRight.setLayout(flowRight);
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        pnlSouthLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnlSouthRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        cmbFormatter = new JComboBox<>();
        cmbFormatter.addItem("Select to apply format");
        cmbFormatter.addItem("Remove all empty lines");
        cmbFormatter.addItem("Remove all // comments");
        cmbFormatter.addItem("Remove all duplicate lines");
        cmbFormatter.addItem("Apply all");
        cmbFormatter.addItemListener(e -> {
            if (cmbFormatter.getSelectedIndex() == 1) {
                String[] linkArr = taDisplay.getText().trim().split("\n");
                StringBuilder sb = new StringBuilder();
                for (String link : linkArr) {
                    if (!link.isBlank()) {
                        link = link.trim();
                        sb.append(link).append("\n");
                    }
                }
                taDisplay.setText(sb.toString().trim());
                cmbFormatter.setSelectedIndex(0);
            }
            if (cmbFormatter.getSelectedIndex() == 2) {
                String[] linkArr = taDisplay.getText().trim().split("\n");
                StringBuilder sb = new StringBuilder();
                for (String link : linkArr) {
                    if (!link.startsWith("//")) {
                        sb.append(link).append("\n");
                    }
                }
                taDisplay.setText(sb.toString().trim());
                cmbFormatter.setSelectedIndex(0);
            }
            if (cmbFormatter.getSelectedIndex() == 3) {
                // TODO: Add formatting for duplicates
            }
            if (cmbFormatter.getSelectedIndex() == 4) {
                // TODO: Add combination of all formats
            }
        });
        btnSaveChanges = new JButton("Save & Submit");
        btnSaveChanges.addActionListener(e -> {
            taDisplayMW.setText(this.taDisplay.getText());
            this.setVisible(false);
        });
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            if (this.taDisplay.getText().isBlank()) {
                JOptionPane.showMessageDialog(EditDialog.this, "This list has already been cleared!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.taDisplay.setText("");
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            this.setVisible(false);
        });
        pnlSouthLeft.add(cmbFormatter);
        pnlSouthRight.add(btnSaveChanges);
        pnlSouthRight.add(btnClear);
        pnlSouthRight.add(btnCancel);
        pnlSouth.add(pnlSouthLeft, BorderLayout.WEST);
        pnlSouth.add(pnlSouthRight, BorderLayout.EAST);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }

}
