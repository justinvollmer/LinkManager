package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DownloadManagerDialog extends JDialog {
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JPanel pnlActionBar;
    private JScrollPane scrollPane;
    private JTable table;
    private List<LinkEntry> linkEntryList;
    private JPanel pnlCenterAction1;
    private JLabel lblNamingSystem;
    private JTextField tfNamingSystem;
    private JPanel pnlCenterAction2;
    private JLabel lblPos1;
    private JTextField tfPos1;
    private JLabel lblToPos2;
    private JTextField tfPos2;
    private JButton btnApplyNaming;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JPanel pnlSouthRight;
    private JButton btnHow;
    private JButton btnCancel;

    public DownloadManagerDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Download Manager");
        super.setLocation(mw.getLocation());
        super.setResizable(false);

        // SAVING LINKS IN ENTRY AND ADDING TO ARRAYLIST
        linkEntryList = new ArrayList<>();
        String[] linkArr = taDisplayMW.getText().trim().split("\n");
        int id = 0;
        for(String link : linkArr) {
            id++;
            linkEntryList.add(new LinkEntry(id, link, "", "ready"));
        }

        // HEADER
        pnlNorth = new JPanel();
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlNorth.setLayout(flowCenter);
        pnlNorth.setBorder(new EmptyBorder(5, 5, 0, 5));
        lblTitle = new JLabel("Download Manager");
        lblTitle.setFont(new Font(null, Font.BOLD, 20));
        pnlNorth.add(lblTitle);

        // DISPLAY
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 10, 0, 5));
        pnlCenter.setLayout(new BorderLayout());
        table = new JTable(new DownloadManagerTableModel(linkEntryList));
        pnlActionBar = new JPanel();
        pnlActionBar.setBorder(new EmptyBorder(0, 5, 0, 0));
        pnlActionBar.setLayout(new GridLayout(10, 1));
        pnlCenterAction1 = new JPanel();
        pnlCenterAction2 = new JPanel();
        lblNamingSystem = new JLabel("file name: ");
        tfNamingSystem = new JTextField();
        tfNamingSystem.setColumns(20);
        lblPos1 = new JLabel("Pos: ");
        tfPos1 = new JTextField();
        tfPos1.setColumns(3);
        lblToPos2 = new JLabel("to Pos: ");
        tfPos2 = new JTextField();
        tfPos2.setColumns(3);
        btnApplyNaming = new JButton("Apply Name");
        scrollPane = new JScrollPane();
        pnlCenterAction1.add(lblNamingSystem);
        pnlCenterAction1.add(tfNamingSystem);
        pnlCenterAction2.add(lblPos1);
        pnlCenterAction2.add(tfPos1);
        pnlCenterAction2.add(lblToPos2);
        pnlCenterAction2.add(tfPos2);
        pnlCenterAction2.add(btnApplyNaming);
        
        pnlActionBar.add(pnlCenterAction1);
        pnlActionBar.add(pnlCenterAction2);
        scrollPane.setViewportView(table);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        pnlCenter.add(pnlActionBar, BorderLayout.EAST);

        // SOUTH
        pnlSouth = new JPanel();
        pnlSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnlSouth.setLayout(new BorderLayout());
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouthLeft = new JPanel();
        pnlSouthLeft.setLayout(new FlowLayout());
        btnHow = new JButton("How it works");
        btnHow.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "The Download Manager allows you to download media such as images or gifs (videos aren't supported as of right now).", "How it works", JOptionPane.INFORMATION_MESSAGE);
        });
        pnlSouthRight = new JPanel();
        pnlSouthRight.setLayout(flowRight);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            this.setVisible(false);
        });
        pnlSouthLeft.add(btnHow);
        pnlSouthRight.add(btnCancel);
        pnlSouth.add(pnlSouthLeft, BorderLayout.WEST);
        pnlSouth.add(pnlSouthRight, BorderLayout.EAST);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
