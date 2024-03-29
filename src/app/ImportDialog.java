package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ImportDialog extends JDialog {
    private JPanel pnlCenter;
    private JPanel pnlMode;
    private JLabel lblMode;
    private JComboBox<String> cmbMode;
    private JPanel pnlAppend;
    private JLabel lblAppend;
    private JCheckBox chckAppend;
    private JPanel pnlSouth;
    private JButton btnCancel;
    private JButton btnSelect;

    public ImportDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Link-Manager - Import");
        super.setLocation(mw.getLocation());
        super.setResizable(false);
        super.setBounds(mw.getX(), mw.getY(), 250, 150);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlCenter.setLayout(new GridLayout(2, 1));
        pnlMode = new JPanel();
        pnlMode.setLayout(flowCenter);
        lblMode = new JLabel("Mode:");
        cmbMode = new JComboBox<>();
        cmbMode.addItem("Textfile (txt)");
        pnlMode.add(lblMode);
        pnlMode.add(cmbMode);
        cmbMode.setEnabled(false);
        pnlAppend = new JPanel();
        pnlAppend.setLayout(flowCenter);
        lblAppend = new JLabel("Append text: ");
        chckAppend = new JCheckBox();
        chckAppend.setSelected(false);
        pnlAppend.add(lblAppend);
        pnlAppend.add(chckAppend);
        pnlCenter.add(pnlMode);
        pnlCenter.add(pnlAppend);


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
            if (this.cmbMode.getSelectedIndex() == 0) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
                fc.setDialogTitle("MassLinkOpener - Import");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (.txt)", "txt");
                fc.setFileFilter(filter);
                int retVal = fc.showOpenDialog(mw);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        if (!this.chckAppend.isSelected()) {
                            taDisplayMW.setText("");
                        } else {
                            taDisplayMW.append("\n");
                        }
                        while ((line = br.readLine()) != null) {
                            taDisplayMW.append(line + "\n");
                        }
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, "Error: " + io.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        pnlSouth.add(btnSelect);
        pnlSouth.add(btnCancel);

        try {
            updateTheme();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ImportDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    pnlCenter, pnlMode, lblMode, cmbMode, pnlAppend,
                    lblAppend, chckAppend, pnlSouth, btnCancel,
                    btnSelect
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
