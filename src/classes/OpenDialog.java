package classes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class OpenDialog extends JDialog {
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

    public OpenDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("MassLinkOpener - Open");
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
        cmbMode.addItem("Readable (txt)");
        cmbMode.addItem("Hash");
        pnlMode.add(lblMode);
        pnlMode.add(cmbMode);
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
            if(this.cmbMode.getSelectedIndex() == 0) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (.txt)", "txt");
                fc.setFileFilter(filter);
                int retVal = fc.showOpenDialog(mw);
                if(retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String zeile;
                        if (!this.chckAppend.isSelected()) {
                            taDisplayMW.setText("");
                        } else {
                            taDisplayMW.append("\n");
                        }
                        while((zeile = br.readLine()) != null) {
                            taDisplayMW.append(zeile + "\n");
                        }
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, io.getMessage());
                    }
                }
            }
            if(this.cmbMode.getSelectedIndex() == 1) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("MassList (.mll)", "mll");
                fc.setFileFilter(filter);
                int retVal = fc.showOpenDialog(mw);
                if(retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try(ObjectInputStream o_in = new ObjectInputStream(new FileInputStream(file))) {
                        Object o = o_in.readObject();
                        if(o instanceof InputObject) {
                            if (!this.chckAppend.isSelected()) {
                                taDisplayMW.setText(((InputObject) o).getInput());
                            } else {
                                taDisplayMW.append("\n" + ((InputObject) o).getInput());
                            }
                        }
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, io.getMessage());
                    } catch (ClassNotFoundException cnf) {
                        JOptionPane.showMessageDialog(mw, cnf.getMessage());
                    }
                }
            }
        });
        pnlSouth.add(btnSelect);
        pnlSouth.add(btnCancel);

        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.setVisible(true);
    }

}
