package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EncryptionDialog extends JDialog {
    private boolean eligibleChange;
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenterOriginal;
    private JLabel lblCenterOriginal;
    private JScrollPane scrollPaneOriginal;
    private JTextArea taDisplayOriginal;
    private JPanel pnlCenterPreview;
    private JLabel getLblCenterPreview;
    private JScrollPane scrollPanePeview;
    private JTextArea taDisplayPreview;
    private JButton btnUse;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JButton btnHow;
    private JPanel pnlSouthRight;
    private JButton btnEncrypt;
    private JButton btnDecrypt;
    private JButton btnCancel;

    public EncryptionDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Encryption");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        eligibleChange = true;

        // HEADER
        pnlNorth = new JPanel();
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlNorth.setLayout(flowCenter);
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTitle = new JLabel("Mass Link Opener: Encryption");
        lblTitle.setFont(new Font(null, Font.BOLD, 20));
        pnlNorth.add(lblTitle);

        // DISPLAY - ORIGINAL
        pnlCenterOriginal = new JPanel();
        lblCenterOriginal = new JLabel("Original");
        pnlCenterOriginal.setBorder(new EmptyBorder(0, 5, 0, 0));
        pnlCenterOriginal.setLayout(new BorderLayout());
        scrollPaneOriginal = new JScrollPane();
        taDisplayOriginal = new JTextArea();
        taDisplayOriginal.setColumns(40);
        taDisplayOriginal.setRows(25);
        taDisplayOriginal.setText(taDisplayMW.getText().trim());
        taDisplayOriginal.setEditable(false);
        scrollPaneOriginal.setViewportView(taDisplayOriginal);
        pnlCenterOriginal.add(lblCenterOriginal, BorderLayout.NORTH);
        pnlCenterOriginal.add(scrollPaneOriginal, BorderLayout.CENTER);

        // DISPLAY - PREVIEW
        pnlCenterPreview = new JPanel();
        getLblCenterPreview = new JLabel("Preview");
        pnlCenterPreview.setBorder(new EmptyBorder(0, 0, 0, 5));
        pnlCenterPreview.setLayout(new BorderLayout());
        scrollPanePeview = new JScrollPane();
        taDisplayPreview = new JTextArea();
        taDisplayPreview.setColumns(40);
        taDisplayPreview.setRows(25);
        taDisplayPreview.setText("There are no changes applied to your list.");
        taDisplayPreview.setEditable(false);
        scrollPanePeview.setViewportView(taDisplayPreview);
        btnUse = new JButton("Use");
        btnUse.addActionListener(e -> {
            taDisplayMW.setText(taDisplayPreview.getText());
            super.setVisible(false);
        });
        btnUse.setEnabled(false);
        pnlCenterPreview.add(getLblCenterPreview, BorderLayout.NORTH);
        pnlCenterPreview.add(scrollPanePeview, BorderLayout.CENTER);
        pnlCenterPreview.add(btnUse, BorderLayout.SOUTH);

        // BUTTONS
        pnlSouth = new JPanel();
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        pnlSouth.setLayout(new BorderLayout());
        pnlSouthLeft = new JPanel();
        pnlSouthLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnlSouthLeft.setLayout(new FlowLayout());
        btnHow = new JButton("How it works");
        btnHow.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "First, please make sure that you set the 256bit Key in the settings tab. " +
                    "\nNow you can simply press encrypt or decrypt." +
                    "\nWe recommend you to only encrypt a list ONCE." +
                    "\nAlso make sure to keep your key also somewhere outside of the application.", "How it works", JOptionPane.INFORMATION_MESSAGE);
        });
        pnlSouthRight = new JPanel();
        pnlSouthRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnlSouthRight.setLayout(flowRight);
        btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(e -> {
            if (eligibleChange) {
                try {
                    String key = Config.getProperties("encryptionkey");
                    Encryption crypto = new Encryption(key, "AES");
                    String encrypted = crypto.encrypt(taDisplayOriginal.getText());
                    taDisplayPreview.setText(encrypted);
                    eligibleChange = false;
                    btnUse.setEnabled(true);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "An error occured while setting the encryption key", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "The list has already been edited. " +
                        "\nSave or discard changes and reopen this window to apply new changes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            if (eligibleChange) {
                try {
                    String key = Config.getProperties("encryptionkey");
                    Encryption crypto = new Encryption(key, "AES");
                    String decrypted = crypto.decrypt(taDisplayOriginal.getText());
                    taDisplayPreview.setText(decrypted);
                    eligibleChange = false;
                    btnUse.setEnabled(true);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "An error occured while setting the encryption key. " +
                            "\nOr the key cannot decrypt the list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "The list has already been edited. " +
                        "\nSave or discard changes and reopen this window to apply new changes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlSouthLeft.add(btnHow);
        pnlSouthRight.add(btnEncrypt);
        pnlSouthRight.add(btnDecrypt);
        pnlSouthRight.add(btnCancel);
        pnlSouth.add(pnlSouthLeft, BorderLayout.WEST);
        pnlSouth.add(pnlSouthRight, BorderLayout.EAST);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenterOriginal, BorderLayout.CENTER);
        super.getContentPane().add(pnlCenterPreview, BorderLayout.EAST);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
