package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EncryptionDialog extends JDialog {
    private boolean eligibleChange;
    private String encrypted;
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenterOriginal;
    private JLabel lblCenterOriginal;
    private JScrollPane scrollPaneOriginal;
    private JTextArea taDisplayOriginal;
    private JPanel pnlCenterPreview;
    private JLabel lblCenterPreview;
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
        lblTitle = new JLabel("Link-Manager: Encryption");
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
        lblCenterPreview = new JLabel("Preview");
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
            taDisplayMW.setText(encrypted);
            super.setVisible(false);
        });
        btnUse.setEnabled(false);
        pnlCenterPreview.add(lblCenterPreview, BorderLayout.NORTH);
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
            JOptionPane.showMessageDialog(this, "First, please make sure that you set the 256bit Key in the settings tab." +
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
                    encrypted = crypto.encrypt(taDisplayOriginal.getText());
                    taDisplayPreview.setText(splitString(encrypted, 60));
                    eligibleChange = false;
                    btnUse.setEnabled(true);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "An error occurred while setting the encryption key", "Error", JOptionPane.ERROR_MESSAGE);
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
                    encrypted = crypto.decrypt(taDisplayOriginal.getText());
                    taDisplayPreview.setText(encrypted);
                    eligibleChange = false;
                    btnUse.setEnabled(true);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "An error occurred while setting the encryption key. " +
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

        try {
            updateTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(EncryptionDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenterOriginal, BorderLayout.CENTER);
        super.getContentPane().add(pnlCenterPreview, BorderLayout.EAST);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }

    private String splitString(String text, int n) {
        String[] arr = text.split("(?<=\\G.{" + n + "})");
        StringBuilder res = new StringBuilder();
        for (String s : arr) {
            res.append(s + "\n");
        }
        return res.toString();
    }

    private void updateTheme() throws Exception {
        String theme;
        try {
            theme = Config.getTheme();
        } catch (Exception e) {
            throw new Exception("An error occured while accessing the properties file");
        }

        if (theme.equalsIgnoreCase("dark")) {
            Color darkGray = Color.DARK_GRAY;
            Color white = Color.WHITE;
            List<Component> components = Arrays.asList(
                    pnlNorth, lblTitle, pnlCenterOriginal, lblCenterOriginal,
                    scrollPaneOriginal, taDisplayOriginal, pnlCenterPreview, lblCenterPreview,
                    scrollPanePeview, taDisplayPreview, btnUse, pnlSouth, pnlSouthLeft, btnHow,
                    pnlSouthRight, btnEncrypt, btnDecrypt, btnCancel
            );
            for (Component component : components) {
                component.setBackground(darkGray);
                component.setForeground(white);
            }
            for (JButton button : Arrays.asList(btnUse, btnHow, btnEncrypt, btnDecrypt, btnCancel)) {
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        }
    }
}
