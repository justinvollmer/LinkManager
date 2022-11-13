package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DownloadManagerDialog extends JDialog {
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JScrollPane scrollPane;
    private JTable table;
    private List<LinkEntry> linkEntryList;
    private JPanel pnlCenterRight;
    private JLabel lblNamingSystem ;
    private JTextField tfNamingSystem;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JPanel pnlSouthRight;
    private JButton btnHow;

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
        pnlCenterRight = new JPanel();
        pnlCenterRight.setBorder(new EmptyBorder(0, 5, 0, 0));
        lblNamingSystem = new JLabel("file name: ");
        tfNamingSystem = new JTextField();
        tfNamingSystem.setColumns(30);
        scrollPane = new JScrollPane();
        pnlCenterRight.add(lblNamingSystem);
        pnlCenterRight.add(tfNamingSystem);
        scrollPane.setViewportView(table);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        pnlCenter.add(pnlCenterRight, BorderLayout.EAST);

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

        pnlSouthLeft.add(btnHow);

        pnlSouth.add(pnlSouthLeft, BorderLayout.WEST);
        pnlSouth.add(pnlSouthRight, BorderLayout.EAST);

        super.getContentPane().add(pnlNorth, BorderLayout.NORTH);
        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.pack();
        super.setVisible(true);
    }
}
