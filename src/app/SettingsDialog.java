package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.NoSuchAlgorithmException;

public class SettingsDialog extends JDialog {
    private JPanel pnlNorth;
    private JLabel lblAlgorithm;
    private JComboBox<String> cmbAlgorithm;
    private JPanel pnlCenter;
    private JLabel lblEncryptionKey;
    private JTextArea taEncryptionKey;
    private JScrollPane scrollPane;
    private JPanel pnlSouth;
    private JButton btnGenerateKey;
    private JButton btnClear;
    private JButton btnCopyToClipboard;
    private JButton btnApplyChanges;
    private JButton btnCancel;

    public SettingsDialog(MainWindow mw) {
        super(mw, true);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setTitle("Settings");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        // NORTH
        pnlNorth = new JPanel();
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblAlgorithm = new JLabel("Algorithm:");
        cmbAlgorithm = new JComboBox<>();
        cmbAlgorithm.addItem("AES");
        cmbAlgorithm.setEnabled(false);
        pnlNorth.add(lblAlgorithm);
        pnlNorth.add(cmbAlgorithm);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(0, 5, 0, 5));
        lblEncryptionKey = new JLabel("Encryption Key (256bit):");
        taEncryptionKey = new JTextArea();
        taEncryptionKey.setColumns(40);
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
        btnGenerateKey = new JButton("Generate Key");
        btnGenerateKey.addActionListener(e -> {
            try {
                taEncryptionKey.setText(Encryption.generateKey());
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            taEncryptionKey.setText("");
        });
        btnCopyToClipboard = new JButton("Copy Key");
        btnCopyToClipboard.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(taEncryptionKey.getText().trim());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        btnApplyChanges = new JButton("Apply Changes");
        btnApplyChanges.addActionListener(e -> {
            try {
                Config.setProperties("encryptionkey", taEncryptionKey.getText().trim());
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(this, "An error occured while setting the encryption key", "Error", JOptionPane.ERROR_MESSAGE);
            }
            super.setVisible(false);
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlSouth.add(btnGenerateKey);
        pnlSouth.add(btnClear);
        pnlSouth.add(btnCopyToClipboard);
        pnlSouth.add(btnApplyChanges);
        pnlSouth.add(btnCancel);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
