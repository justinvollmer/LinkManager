package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

public class MainWindow extends JFrame {
    private String displayPlaceholder;
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JScrollPane scrollPane;
    private JTextArea taDisplay;
    private JPanel pnlSouth;
    private JButton btnDownloadManager;
    private JButton btnEdit;
    private JButton btnClear;
    private JButton btnExecute;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mniImport;
    private JMenuItem mniSaveAs;
    private JMenuItem mniConvert;
    private JMenuItem mniExit;
    private JMenu mnHelp;
    private JMenuItem mniAbout;

    public MainWindow() {
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setTitle("MassLinkOpener");
        super.setLayout(new BorderLayout());
        super.setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/ico/icon.png");
        super.setIconImage(icon);

        displayPlaceholder = "https://example.com/image.jpg\nhttps://example.com/image.jpg\nhttps://example.com/image.jpg\nhttps://example.com/image.jpg";

        // MENUBAR
        menuBar = new JMenuBar();
        mnFile = new JMenu("File");
        mniImport = new JMenuItem("Import...");
        mniImport.addActionListener(e -> {
            new OpenDialog(MainWindow.this, this.taDisplay);
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
        mnHelp = new JMenu("Help");
        mniAbout = new JMenuItem("About");
        mniAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(MainWindow.this, "©️ Justin Vollmer \nVisit me on GitHub: Jvst1n01", "About", JOptionPane.INFORMATION_MESSAGE);
        });
        menuBar.add(mnFile);
        mnFile.add(mniImport);
        mnFile.add(mniSaveAs);
        mnFile.add(mniConvert);
        mnFile.add(mniExit);
        menuBar.add(mnHelp);
        mnHelp.add(mniAbout);

        mnFile.setMnemonic(KeyEvent.VK_F);
        mniImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mniSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mniConvert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
        mniExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        mnHelp.setMnemonic(KeyEvent.VK_H);

        // HEADER
        pnlNorth = new JPanel();
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlNorth.setLayout(flowCenter);
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTitle = new JLabel("Mass Link Opener: Preview");
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

        // BUTTONS
        pnlSouth = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(flowRight);
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        btnDownloadManager = new JButton("Download Manager");
        btnDownloadManager.addActionListener(e -> {
            // TODO: Download Manager Dialog
        });
        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> {
            new EditDialog(MainWindow.this, this.taDisplay);
        });

        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            taDisplay.setText(displayPlaceholder);
        });
        btnExecute = new JButton("Execute");
        btnExecute.addActionListener(e -> {
            int yesNo = JOptionPane.showConfirmDialog(MainWindow.this, "Please only use this when you have a short list. " +
                    "\nThe links will be opened with no delay. " +
                    "\nThe websites might detect this as a spam. " +
                    "\nIf you have a long list, please \"convert to a batch file\"", "Warning", JOptionPane.YES_NO_OPTION);
            if(yesNo == 0) {
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
        pnlSouth.add(btnEdit);
        pnlSouth.add(btnClear);
        pnlSouth.add(btnExecute);

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
