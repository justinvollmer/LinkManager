package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private JPanel pnlCenter;
    private JLabel lblEncryptionKey;
    private JTextArea taEncryptionKey;
    private JScrollPane scrollPane;
    private JPanel pnlSouth;
    private JButton btnSaveChanges;
    private JButton btnCancel;

    public SettingsDialog(MainWindow mw) {
        super(mw, true);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setTitle("Settings");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblEncryptionKey = new JLabel("Encryption Key:");
        taEncryptionKey = new JTextArea();
        taEncryptionKey.setColumns(40);
        taEncryptionKey.setRows(10);
        scrollPane = new JScrollPane();
        try {
            taEncryptionKey.setText(Config.getProperties("encryptionkey"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occured while setting the encryption key", "Error", JOptionPane.ERROR_MESSAGE);
        }
        scrollPane.setViewportView(taEncryptionKey);
        pnlCenter.add(lblEncryptionKey);
        pnlCenter.add(scrollPane);

        // SOUTH
        pnlSouth = new JPanel();
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(flowRight);
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.addActionListener(e -> {
            try {
                Config.setProperties("encryptionkey", taEncryptionKey.getText());
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(this, "An error occured while setting the encryption key", "Error", JOptionPane.ERROR_MESSAGE);
            }
            super.setVisible(false);
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlSouth.add(btnSaveChanges);
        pnlSouth.add(btnCancel);

        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
