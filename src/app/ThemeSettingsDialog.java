package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ThemeSettingsDialog extends JDialog {
    private JPanel pnlCenter;
    private JLabel lblTheme;
    private JComboBox<String> cmbTheme;
    private JPanel pnlSouth;
    private JButton btnApplyChanges;
    private JButton btnCancel;
    private MainWindow mw;
    private String originalTheme;

    public ThemeSettingsDialog(MainWindow mw) {
        super(mw, true);
        this.mw = mw;
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setTitle("Theme Settings");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        try {
            originalTheme = Config.getTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThemeSettingsDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Initialize objects beforhand so they do not cause an error while setting a theme
        pnlSouth = new JPanel();
        btnApplyChanges = new JButton("Apply Changes");
        btnCancel = new JButton("Cancel");

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTheme = new JLabel("Theme:");
        cmbTheme = new JComboBox<>();
        cmbTheme.addItem("Light");
        cmbTheme.addItem("Dark");
        cmbTheme.addItemListener(e -> {
            if (cmbTheme.getSelectedIndex() == 0) {
                setTheme("light");
            }
            if (cmbTheme.getSelectedIndex() == 1) {
                setTheme("dark");
            }
        });
        try {
            if (Config.getTheme().equalsIgnoreCase("light")) {
                cmbTheme.setSelectedIndex(0);
            } else if (Config.getTheme().equalsIgnoreCase("dark")) {
                cmbTheme.setSelectedIndex(1);
            } else {
                cmbTheme.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThemeSettingsDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        pnlCenter.add(lblTheme);
        pnlCenter.add(cmbTheme);

        // SOUTH
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        btnApplyChanges.addActionListener(e -> {
            try {
                Config.setProperties("theme", cmbTheme.getSelectedItem().toString().toLowerCase());
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(ThemeSettingsDialog.this, "An error occured while setting the theme", "Error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                if (originalTheme.equalsIgnoreCase("dark") && Config.getTheme().equalsIgnoreCase("light")) {
                    this.mw.setThemeToLight();
                }
                if (originalTheme.equalsIgnoreCase("light") && Config.getTheme().equalsIgnoreCase("dark")) {
                    this.mw.updateTheme();
                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(ThemeSettingsDialog.this, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            super.setVisible(false);
        });
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        pnlSouth.add(btnApplyChanges);
        pnlSouth.add(btnCancel);

        try {
            updateTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThemeSettingsDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    pnlCenter, lblTheme, cmbTheme, pnlSouth, btnApplyChanges,
                    btnCancel
            );
            for (Component component : components) {
                component.setBackground(darkGray);
                component.setForeground(white);
            }
            for (JButton button : Arrays.asList(btnApplyChanges, btnCancel)) {
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        }
    }

    private void setTheme(String theme) {
        if (theme.equalsIgnoreCase("light")) {
            Color black = Color.BLACK;
            List<Component> components = Arrays.asList(
                    pnlCenter, lblTheme, cmbTheme, pnlSouth, btnApplyChanges,
                    btnCancel
            );
            for (Component component : components) {
                component.setBackground(null);
                component.setForeground(black);
            }
            for (JButton button : Arrays.asList(btnApplyChanges, btnCancel)) {
                button.setFocusPainted(true);
                button.setContentAreaFilled(true);
            }
        } else if (theme.equalsIgnoreCase("dark")) {
            Color darkGray = Color.DARK_GRAY;
            Color white = Color.WHITE;
            List<Component> components = Arrays.asList(
                    pnlCenter, lblTheme, cmbTheme, pnlSouth, btnApplyChanges,
                    btnCancel
            );
            for (Component component : components) {
                component.setBackground(darkGray);
                component.setForeground(white);
            }
            for (JButton button : Arrays.asList(btnApplyChanges, btnCancel)) {
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        }
    }
}
