package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SaveDialog extends JDialog {
    private JPanel pnlCenter;
    private JLabel lblMode;
    private JComboBox<String> cmbMode;
    private JPanel pnlSouth;
    private JButton btnCancel;
    private JButton btnSelect;

    public SaveDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Link-Manager - Save");
        super.setLocation(mw.getLocation());
        super.setResizable(false);
        super.setBounds(mw.getX(), mw.getY(), 250, 125);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlCenter.setLayout(flowCenter);
        lblMode = new JLabel("Mode:");
        cmbMode = new JComboBox<>();
        cmbMode.addItem("Textfile (txt)");
        pnlCenter.add(lblMode);
        pnlCenter.add(cmbMode);
        cmbMode.setEnabled(false);

        // SOUTH
        pnlSouth = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(flowRight);
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        btnSelect = new JButton("Select");
        btnSelect.addActionListener(e -> {
            super.setVisible(false);
            if (cmbMode.getSelectedIndex() == 0) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
                fc.setDialogTitle("MassLinkOpener - Save");
                fc.setAcceptAllFileFilterUsed(false); // "All files" option disabled
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (.txt)", "txt");
                fc.setFileFilter(filter);
                int retVal = fc.showSaveDialog(mw);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fc.getSelectedFile().getAbsolutePath() + ".txt");
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(taDisplayMW.getText());
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, io.getMessage());
                    }
                }
            }
        });
        pnlSouth.add(btnSelect);
        pnlSouth.add(btnCancel);

        try {
            updateTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(SaveDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
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
                    pnlCenter, lblMode, cmbMode, pnlSouth, btnCancel, btnSelect
            );
            for (Component component : components) {
                component.setBackground(darkGray);
                component.setForeground(white);
            }
            for (JButton button : Arrays.asList(btnCancel, btnSelect)) {
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        }
    }

}
