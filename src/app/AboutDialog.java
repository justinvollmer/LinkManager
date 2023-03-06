package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AboutDialog extends JDialog {
    private JPanel pnlCenter;
    private JLabel lblCopyright;
    private JLabel lblGithub;
    private JLabel lblVersion;
    private JPanel pnlSouth;
    private JButton btnProfile;
    private JButton btnClose;

    public AboutDialog(MainWindow mw) {
        super(mw, true);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("About");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        pnlCenter.setBorder(new EmptyBorder(10, 10, 0, 10));
        lblCopyright = new JLabel("©️ Justin Vollmer");
        lblGithub = new JLabel("Visit me on GitHub:     justinvollmer [CLICK]");
        lblGithub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    MainWindow.openUrl("https://github.com/justinvollmer");
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(AboutDialog.this, "An error occurred while opening the Github Profile.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lblVersion = new JLabel("Version:     " + Version.getSoftwareVersion());
        pnlCenter.add(lblCopyright, BorderLayout.NORTH);
        pnlCenter.add(lblGithub, BorderLayout.CENTER);
        pnlCenter.add(lblVersion, BorderLayout.SOUTH);

        // BUTTONS
        pnlSouth = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(flowRight);
        pnlSouth.setBorder(new EmptyBorder(0, 10, 5, 10));
        btnProfile = new JButton("Github Profile");
        btnProfile.addActionListener(e -> {
            try {
                MainWindow.openUrl("https://github.com/justinvollmer");
            } catch (IOException io) {
                JOptionPane.showMessageDialog(AboutDialog.this, "An error occurred while opening the Github Profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlSouth.add(btnProfile);
        pnlSouth.add(btnClose);

        try {
            updateTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(AboutDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
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
                    pnlCenter, lblCopyright, lblGithub, lblVersion, pnlSouth, btnProfile, btnClose
            );
            for (Component component : components) {
                component.setBackground(darkGray);
                component.setForeground(white);
            }
            for (JButton button : Arrays.asList(btnProfile, btnClose)) {
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        }
    }
}
