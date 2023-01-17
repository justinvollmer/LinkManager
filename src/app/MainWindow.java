package app;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private String displayPlaceholder;
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JScrollPane scrollPane;
    private JTextArea taDisplay;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JPanel pnlSouthRight;
    private JButton btnDownloadManager;
    private JButton btnEncryption;
    private JButton btnEdit;
    private JButton btnClear;
    private JButton btnReset;
    private JButton btnExecute;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mniImport;
    private JMenuItem mniSaveAs;
    private JMenuItem mniConvert;
    private JMenuItem mniExit;
    private JMenu mnSettings;
    private JMenuItem mniEncryption;
    private JMenu mnHelp;
    private JMenuItem mniAbout;

    public MainWindow() {
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setTitle("Link-Manager");
        super.setLayout(new BorderLayout());
        super.setResizable(false);
        // Image icon = Toolkit.getDefaultToolkit().getImage("icon.png"); // icon is used when installed via installer
        Image icon = Toolkit.getDefaultToolkit().getImage("src/ico/icon.png"); // icon is used when pulled from repo
        super.setIconImage(icon);

        displayPlaceholder = "https://example.com/image.jpg\nhttps://example.com/image.jpg\nhttps://example.com/image.jpg\nhttps://example.com/image.jpg";

        // MENUBAR
        menuBar = new JMenuBar();
        mnFile = new JMenu("File");
        mniImport = new JMenuItem("Import...");
        mniImport.addActionListener(e -> {
            new ImportDialog(MainWindow.this, this.taDisplay);
        });
        mniSaveAs = new JMenuItem("Save as...");
        mniSaveAs.addActionListener(e -> {
            new SaveDialog(MainWindow.this, this.taDisplay);
        });
        mniConvert = new JMenuItem("Convert...");
        mniConvert.addActionListener(e -> {
            new ConversionSettingsDialog(MainWindow.this, this.taDisplay);
        });
        mniExit = new JMenuItem("Exit");
        mniExit.addActionListener(e -> {
            System.exit(0);
        });
        mnSettings = new JMenu("Settings");
        mniEncryption = new JMenuItem("Encryption");
        mniEncryption.addActionListener(e -> {
            new SettingsDialog(MainWindow.this);
        });
        mnHelp = new JMenu("Help");
        mniAbout = new JMenuItem("About");
        mniAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(MainWindow.this, "©️ Justin Vollmer \nVisit me on GitHub: justinvollmer \nVersion:     " + Version.getSoftwareVersion(), "Link-Manager - About", JOptionPane.INFORMATION_MESSAGE);
        });
        mnFile.add(mniImport);
        mnFile.add(mniSaveAs);
        mnFile.add(mniConvert);
        mnFile.add(mniExit);
        mnSettings.add(mniEncryption);
        mnHelp.add(mniAbout);
        menuBar.add(mnFile);
        menuBar.add(mnSettings);
        menuBar.add(mnHelp);

        mnFile.setMnemonic(KeyEvent.VK_F);
        mniImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mniSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mniConvert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
        mniExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        mnSettings.setMnemonic((KeyEvent.VK_S));
        mnHelp.setMnemonic(KeyEvent.VK_H);

        // HEADER
        pnlNorth = new JPanel();
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlNorth.setLayout(flowCenter);
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTitle = new JLabel("Link-Manager: Preview");
        lblTitle.setFont(new Font(null, Font.BOLD, 20));
        pnlNorth.add(lblTitle);

        // DISPLAY
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(0, 5, 0, 5));
        scrollPane = new JScrollPane();
        taDisplay = new JTextArea();
        taDisplay.setColumns(75);
        taDisplay.setRows(25);
        taDisplay.setText(displayPlaceholder);
        taDisplay.setEditable(false);
        scrollPane.setViewportView(taDisplay);
        pnlCenter.add(scrollPane);

        // Drag & Drop
        new DropTarget(taDisplay, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent event) {
                int yesNo = JOptionPane.showConfirmDialog(MainWindow.this, "You are about to import one or multiple lists. " +
                        "\nDo you want to append them to your exisiting list?", "Warning", JOptionPane.YES_NO_OPTION);
                if (yesNo == 0) {
                    taDisplay.setText(taDisplay.getText() + "\n");
                }
                if (yesNo == 1) {
                    taDisplay.setText("");
                }
                try {
                    Transferable tr = event.getTransferable();
                    DataFlavor[] flavors = tr.getTransferDataFlavors();
                    for (int i = 0; i < flavors.length; i++) {
                        if (flavors[i].isFlavorJavaFileListType()) {
                            event.acceptDrop(event.getDropAction());
                            List<File> files = (List<File>) tr.getTransferData(flavors[i]);
                            for (File file : files) {
                                if (file.getName().endsWith("txt")) {
                                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                                        String line;
                                        while ((line = br.readLine()) != null) {
                                            taDisplay.append(line + "\n");
                                        }
                                    } catch (IOException io) {
                                        JOptionPane.showMessageDialog(MainWindow.this, "Error: " + io.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            event.dropComplete(true);
                        }
                    }
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(MainWindow.this, "Error: " + io.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedFlavorException ufe) {
                    JOptionPane.showMessageDialog(MainWindow.this, "Error: " + ufe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // BUTTONS
        pnlSouth = new JPanel();
        pnlSouthLeft = new JPanel();
        pnlSouthRight = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        pnlSouthLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnlSouthRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnlSouth.setLayout(new BorderLayout());
        pnlSouthRight.setLayout(flowRight);
        pnlSouthLeft.setLayout(new FlowLayout());
        btnDownloadManager = new JButton("Download Manager");
        btnDownloadManager.addActionListener(e -> {
            if (taDisplay.getText().equals(displayPlaceholder) || taDisplay.getText().isBlank()) {
                JOptionPane.showMessageDialog(MainWindow.this, "Please import or edit the link list!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new DownloadManagerDialog(MainWindow.this, this.taDisplay);
        });
        btnEncryption = new JButton("Encryption");
        btnEncryption.addActionListener(e -> {
            try {
                String key = Config.getProperties("encryptionkey");
                if (!key.isBlank()) {
                    new EncryptionDialog(MainWindow.this, this.taDisplay);
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this, "Please add a encryption key in the settings!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(MainWindow.this, "Please import or edit the link list!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> {
            new EditDialog(MainWindow.this, this.taDisplay);
        });
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            if (taDisplay.getText().isBlank()) {
                JOptionPane.showMessageDialog(MainWindow.this, "This list has already been cleared!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            taDisplay.setText("");
        });
        btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> {
            if (taDisplay.getText().equals(displayPlaceholder)) {
                JOptionPane.showMessageDialog(MainWindow.this, "The list has already been reset!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            taDisplay.setText(displayPlaceholder);
        });
        btnExecute = new JButton("Execute");
        btnExecute.addActionListener(e -> {
            if (taDisplay.getText().equals(displayPlaceholder) || taDisplay.getText().isBlank()) {
                JOptionPane.showMessageDialog(MainWindow.this, "Please import or edit the link list!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int yesNo = JOptionPane.showConfirmDialog(MainWindow.this, "Please only use this when you have a short list. " +
                    "\nThe links will be opened with no delay. " +
                    "\nThe websites might detect this as a spam. " +
                    "\nIf you have a long list, please \"convert to a batch file\"" +
                    "\nDo you want to continue?", "Warning", JOptionPane.YES_NO_OPTION);
            if (yesNo == 0) {
                String completelist = this.taDisplay.getText();
                String[] list = completelist.trim().split("\n");
                for (String s : list) {
                    try {
                        openUrl(s);
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(MainWindow.this, io.getMessage());
                    }
                }
            }
        });
        pnlSouthLeft.add(btnDownloadManager);
        pnlSouthLeft.add(btnEncryption);
        pnlSouthRight.add(btnEdit);
        pnlSouthRight.add(btnClear);
        pnlSouthRight.add(btnReset);
        pnlSouthRight.add(btnExecute);
        pnlSouth.add(pnlSouthLeft, BorderLayout.WEST);
        pnlSouth.add(pnlSouthRight, BorderLayout.EAST);

        super.setJMenuBar(menuBar);
        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }

    public static void openUrl(String url) throws IOException {
        Desktop.getDesktop().browse(URI.create(url));
    }

}
