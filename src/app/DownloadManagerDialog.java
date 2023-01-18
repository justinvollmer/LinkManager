package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadManagerDialog extends JDialog implements Runnable {
    private DownloadManager manager;
    private int interval;
    private Thread downloadingThread;
    private JPanel pnlNorth;
    private JLabel lblTitle;
    private JPanel pnlCenter;
    private JPanel pnlActionBar;
    private JScrollPane scrollPane;
    private JTable table;
    private DownloadManagerTableModel tableModel;
    private List<LinkEntry> linkEntryList;
    private JPanel pnlCenterAction1;
    private JLabel lblNamingSystem;
    private JTextField tfNamingSystem;
    private JPanel pnlCenterAction2;
    private JLabel lblPreviewName;
    private JTextField tfPreviewName;
    private JButton btnApplyNaming;
    private JPanel pnlCenterAction3;
    private JButton btnApplyNameToSelected;
    private JButton btnClearAllName;
    private JPanel pnlCenterAction4;
    private JLabel lblDirectory;
    private JButton btnSelectFolder;
    private JPanel pnlCenterAction5;
    private JTextField tfPath;
    private String noDirectory;
    private JPanel pnlCenterAction6;
    private JLabel lblInterval;
    private JTextField tfInterval;
    private JLabel lblSeconds;
    private JPanel pnlCenterAction7;
    private JButton btnCheckFilenames;
    private JButton btnCheckLinks;
    private JLabel lblDownloadStatus;
    private JTextField tfDownloadStatus;
    private JButton btnDownload;
    private Boolean isDownloading;
    private JPanel pnlSouth;
    private JPanel pnlSouthLeft;
    private JPanel pnlSouthRight;
    private JButton btnHow;
    private JButton btnCancel;
    private String notCheckedLinks;
    private String checkingLinks;
    private String checkingFilenames;
    private String readyForDownload;
    private String inProgress;
    private String errorLinks;
    private String errorFilenames;
    private int maxFilenameLength;

    public DownloadManagerDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("Download Manager");
        super.setLocation(mw.getLocation());
        super.setResizable(true);
        super.setSize((int) (mw.getWidth() * 1.2), mw.getHeight()); // I don't use super.pack() to improve the visibility of the columns

        // SAVING LINKS IN ENTRIES AND ADDING TO ARRAYLIST
        linkEntryList = new ArrayList<>();
        String[] linkArr = taDisplayMW.getText().trim().split("\n");
        int id = 0;
        for (String link : linkArr) {
            if (!link.startsWith("//") && !link.isBlank()) {
                id++;
                linkEntryList.add(new LinkEntry(id, link, "", "not ready"));
            }
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

        // CENTER
        maxFilenameLength = 50;
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 10, 0, 5));
        pnlCenter.setLayout(new BorderLayout());
        tableModel = new DownloadManagerTableModel(linkEntryList);
        table = new JTable(tableModel);

        tableModel.resetColumnWidth(table);

        pnlActionBar = new JPanel();
        pnlActionBar.setBorder(new EmptyBorder(0, 5, 0, 5));
        pnlActionBar.setLayout(new GridLayout(10, 1));
        FlowLayout flowLeft = new FlowLayout();
        flowLeft.setAlignment(FlowLayout.LEFT);
        pnlCenterAction1 = new JPanel();
        pnlCenterAction1.setLayout(flowLeft);
        pnlCenterAction2 = new JPanel();
        pnlCenterAction2.setLayout(flowLeft);
        pnlCenterAction3 = new JPanel();
        pnlCenterAction3.setLayout(flowLeft);
        pnlCenterAction4 = new JPanel();
        pnlCenterAction4.setLayout(flowLeft);
        pnlCenterAction5 = new JPanel();
        pnlCenterAction5.setLayout(flowLeft);
        pnlCenterAction6 = new JPanel();
        pnlCenterAction6.setLayout(flowLeft);
        pnlCenterAction7 = new JPanel();
        pnlCenterAction7.setLayout(flowLeft);
        lblNamingSystem = new JLabel("file name: ");
        tfNamingSystem = new JTextField();
        tfNamingSystem.setFont(new Font(null, Font.PLAIN, 15));
        tfNamingSystem.setColumns(20);
        tfNamingSystem.setDisabledTextColor(Color.black);
        lblPreviewName = new JLabel("Preview: ");
        tfPreviewName = new JTextField();
        tfPreviewName.setColumns(15);
        tfPreviewName.setEnabled(false);
        tfPreviewName.setBackground(Color.white);
        tfPreviewName.setDisabledTextColor(Color.black);
        tfPreviewName.setFont(new Font(null, Font.PLAIN, 15));
        tfPreviewName.setText("'CustomName_'" + "ID");
        btnApplyNaming = new JButton("Apply name to all");
        btnApplyNaming.addActionListener(e -> {
            if (tfNamingSystem.getText().isEmpty()) {
                return;
            }
            for (LinkEntry entry : linkEntryList) {
                try {
                    entry.setFilename(tfNamingSystem.getText());
                } catch (Exception namingException) {
                    JOptionPane.showMessageDialog(this, "An error occured. Please make sure you do not apply a filename with illegal characters.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            tableModel.fireTableDataChanged();
        });
        btnApplyNameToSelected = new JButton("Apply name to selection");
        btnApplyNameToSelected.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            for (int row : selectedRows) {
                linkEntryList.get(row).setFilename(tfNamingSystem.getText());
            }
            tableModel.fireTableDataChanged();
        });
        btnClearAllName = new JButton("Clear all");
        btnClearAllName.addActionListener(e -> {
            for (LinkEntry entry : linkEntryList) {
                entry.setFilename("");
            }
            tableModel.fireTableDataChanged();
        });
        lblDirectory = new JLabel("Directory: ");
        btnSelectFolder = new JButton("Select folder");
        btnSelectFolder.addActionListener(e -> {
            chooseFolder();
        });
        tfPath = new JTextField();
        tfPath.setColumns(30);
        noDirectory = "No directory selected!";
        tfPath.setText(noDirectory);
        tfPath.setEditable(false);
        tfPath.setBackground(Color.white);
        tfPath.setFont(new Font(null, Font.PLAIN, 15));
        lblInterval = new JLabel("Interval");
        tfInterval = new JTextField();
        tfInterval.setColumns(5);
        tfInterval.setDisabledTextColor(Color.black);
        tfInterval.setBackground(Color.white);
        tfInterval.setFont(new Font(null, Font.PLAIN, 15));
        lblSeconds = new JLabel("second(s) / image");
        lblDownloadStatus = new JLabel("Download Status: ");
        tfDownloadStatus = new JTextField();
        tfDownloadStatus.setColumns(20);
        notCheckedLinks = "Please check the links first!";
        checkingLinks = "Checking links...";
        checkingFilenames = "Checking filenames...";
        readyForDownload = "Ready for download!";
        inProgress = "In progress...";
        errorLinks = "Please fix the links!";
        errorFilenames = "Please fix the filenames";
        setDownloadStatus(notCheckedLinks);
        tfDownloadStatus.setEnabled(false);
        tfDownloadStatus.setFont(new Font(null, Font.BOLD, 15));
        btnCheckLinks = new JButton("Check Links for compatibility");
        btnCheckLinks.addActionListener(e -> {
            btnCheckLinks.setEnabled(false);
            setDownloadStatus(checkingLinks);
            boolean eligibleForDownload = true;
            List<String> supportedFiletypes = DownloadManager.getSupportedFiletypes();
            for (LinkEntry entry : linkEntryList) {
                for (String filetype : supportedFiletypes) {
                    if (entry.getLink().contains(filetype.toLowerCase()) || entry.getLink().contains(filetype.toUpperCase())) {
                        entry.setProgress("ready");
                        break;
                    }
                    if ((!entry.getLink().contains(filetype.toLowerCase()) || !entry.getLink().contains(filetype.toUpperCase())) && supportedFiletypes.get(supportedFiletypes.size()-1).equalsIgnoreCase(filetype)) {
                        entry.setProgress("invalid filetype");
                    }
                    if (!isValidLink(entry.getLink())) {
                        entry.setProgress("invalid link");
                    }
                    if ((!entry.getLink().contains(filetype.toLowerCase()) || !entry.getLink().contains(filetype.toUpperCase()))&& supportedFiletypes.get(supportedFiletypes.size()-1).equalsIgnoreCase(filetype) && !isValidLink(entry.getLink())) {
                        entry.setProgress("invalid link");
                    }
                }
                if (eligibleForDownload && !entry.getProgress().equalsIgnoreCase("ready")) {
                    eligibleForDownload = false;
                }
                tableModel.fireTableDataChanged();
            }
            if (eligibleForDownload) {
                btnCheckFilenames.setEnabled(true);
                setDownloadStatus(readyForDownload);
            } else {
                setDownloadStatus(errorLinks);
            }
        });
        btnCheckFilenames = new JButton("Check filenames for compatibility");
        btnCheckFilenames.setEnabled(false);
        btnCheckFilenames.addActionListener(e -> {
            setDownloadStatus(checkingFilenames);
            boolean eligibleForDownload = true;
            boolean errorInCurrentRun = false; // Helps to decipher whether an ERROR status is from the current run or an old error is rechecked
            for (LinkEntry entry : linkEntryList) {
                String filename = entry.getFilename();
                if (filename.length() > maxFilenameLength || filename.isBlank()) {
                    if (eligibleForDownload) {
                        eligibleForDownload = false;
                        setDownloadStatus(errorFilenames);
                    }
                    entry.setProgress("error");
                    errorInCurrentRun = true;
                    tableModel.fireTableDataChanged();
                } else {
                    entry.setProgress("ready");
                    tableModel.fireTableDataChanged();
                }
                for (char illegalChar : DownloadManager.illegalChars) {
                    if (filename.contains(Character.toString(illegalChar))) {
                        if (eligibleForDownload) {
                            eligibleForDownload = false;
                            setDownloadStatus(errorFilenames);
                        }
                        entry.setProgress("error");
                        errorInCurrentRun = true;
                        tableModel.fireTableDataChanged();
                    } else if (!errorInCurrentRun) {
                        entry.setProgress("ready");
                        tableModel.fireTableDataChanged();
                    }
                }
            }
            if (eligibleForDownload) {
                tableModel.lockFilenames(table);
                btnCheckFilenames.setEnabled(false);
                btnDownload.setEnabled(true);
                setDownloadStatus(readyForDownload);
                tfNamingSystem.setEnabled(false);
                btnApplyNaming.setEnabled(false);
                btnApplyNameToSelected.setEnabled(false);
                btnClearAllName.setEnabled(false);
            }
        });
        isDownloading = false;
        btnDownload = new JButton("Start Download");
        btnDownload.setEnabled(false);
        btnDownload.addActionListener(e -> {
            this.manager = new DownloadManager(tfPath.getText());
            try {
                if ((tfInterval.getText().isBlank() || Integer.parseInt(tfInterval.getText()) < 1) && !isDownloading) {
                    int yesno = JOptionPane.showConfirmDialog(this, "We would recommend using an interval. The website might detect you as spam. \nDo you want to continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (yesno == 1) {
                        return;
                    }
                    tfInterval.setText("0");
                    this.interval = 0;
                } else {
                    this.interval = Integer.parseInt(tfInterval.getText());
                }
            } catch (Exception cannotCastToInt) {
                JOptionPane.showMessageDialog(this, "Your interval input is not a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tfPath.getText().equalsIgnoreCase(noDirectory)) {
                JOptionPane.showMessageDialog(this, "Please select a valid download path first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isDownloading ) {
                btnDownload.setText("Stop Download");
                setDownloadStatus(inProgress);
                btnSelectFolder.setEnabled(false);
                tfInterval.setEnabled(false);
                isDownloading = true;
                btnCancel.setEnabled(false);
                super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                this.downloadingThread = new Thread(this);
                downloadingThread.start();
            } else {
                resetUiAfterDownload();
                isDownloading = false;
                downloadingThread.interrupt();
            }
        });
        scrollPane = new JScrollPane();
        pnlCenterAction1.add(lblNamingSystem);
        pnlCenterAction1.add(tfNamingSystem);
        pnlCenterAction2.add(lblPreviewName);
        pnlCenterAction2.add(tfPreviewName);
        pnlCenterAction2.add(btnApplyNaming);
        pnlCenterAction3.add(btnApplyNameToSelected);
        pnlCenterAction3.add(btnClearAllName);
        pnlCenterAction4.add(lblDirectory);
        pnlCenterAction4.add(btnSelectFolder);
        pnlCenterAction5.add(tfPath);
        pnlCenterAction6.add(lblInterval);
        pnlCenterAction6.add(tfInterval);
        pnlCenterAction6.add(lblSeconds);
        pnlCenterAction7.add(lblDownloadStatus);
        pnlCenterAction7.add(tfDownloadStatus);
        pnlActionBar.add(pnlCenterAction1);
        pnlActionBar.add(pnlCenterAction2);
        pnlActionBar.add(pnlCenterAction3);
        pnlActionBar.add(pnlCenterAction4);
        pnlActionBar.add(pnlCenterAction5);
        pnlActionBar.add(pnlCenterAction6);
        pnlActionBar.add(pnlCenterAction7);
        pnlActionBar.add(btnCheckLinks);
        pnlActionBar.add(btnCheckFilenames);
        pnlActionBar.add(btnDownload);
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
            List<String> filetypes = DownloadManager.getSupportedFiletypes();
            StringBuilder sb = new StringBuilder();
            for (String filetype : filetypes) {
                if (!filetype.equals(filetypes.get(filetypes.size() - 1))) {
                    sb.append(filetype + ", ");
                } else {
                    sb.append(filetype);
                }
            }
            JOptionPane.showMessageDialog(this, "The Download Manager allows you to download media such as images, videos or gifs (videos aren't supported as of right now).\nSupported file types: " + sb, "How it works", JOptionPane.INFORMATION_MESSAGE);
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
        super.setVisible(true);
    }

    private void chooseFolder() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
        fc.setDialogTitle("Download Manager - Select folder");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false); // "All files" option disabled
        int retVal = fc.showOpenDialog(DownloadManagerDialog.this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            tfPath.setText(fc.getSelectedFile().toString() + "\\");
        }
    }

    private void setDownloadStatus(String status) {
        if (status.equalsIgnoreCase(notCheckedLinks)) {
            tfDownloadStatus.setText(notCheckedLinks);
            tfDownloadStatus.setDisabledTextColor(Color.gray);
            return;
        }
        if (status.equalsIgnoreCase(checkingLinks)) {
            tfDownloadStatus.setText(checkingLinks);
            tfDownloadStatus.setDisabledTextColor(Color.orange);
            return;
        }
        if (status.equalsIgnoreCase(checkingFilenames)) {
            tfDownloadStatus.setText(checkingFilenames);
            tfDownloadStatus.setDisabledTextColor(Color.orange);
            return;
        }
        if (status.equalsIgnoreCase(readyForDownload)) {
            tfDownloadStatus.setText(readyForDownload);
            tfDownloadStatus.setDisabledTextColor(Color.black);
            return;
        }
        if (status.equalsIgnoreCase(inProgress)) {
            tfDownloadStatus.setText(inProgress);
            tfDownloadStatus.setDisabledTextColor(Color.blue);
            return;
        }
        if (status.equalsIgnoreCase(errorLinks)) {
            tfDownloadStatus.setText(errorLinks);
            tfDownloadStatus.setDisabledTextColor(Color.red);
            return;
        }
        if (status.equalsIgnoreCase(errorFilenames)) {
            tfDownloadStatus.setText(errorFilenames);
            tfDownloadStatus.setDisabledTextColor(Color.red);
        }
    }

    private boolean isValidLink(String link) {
        try {
            URL url = new URL(link);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private void resetUiAfterDownload() {
        btnDownload.setText("Start Download");
        setDownloadStatus(readyForDownload);
        btnSelectFolder.setEnabled(true);
        btnCancel.setEnabled(true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        tfInterval.setEnabled(true);
        for (LinkEntry entry : linkEntryList) {
            entry.setProgress("ready");
        }
        tableModel.fireTableDataChanged();
    }

    @Override
    public void run() {
        int total = linkEntryList.size();
        int current = 0;
        String currentStatus = tfDownloadStatus.getText();
        try {
            for (LinkEntry entry : linkEntryList) {
                manager.download(entry.getLink(), entry.getFilename(), entry.getId());
                entry.setProgress("done");
                tableModel.fireTableDataChanged();
                current++;
                tfDownloadStatus.setText(currentStatus + " " + current + "/" + total);
                Thread.sleep(interval * 1000L);
            }
            int okQuit = JOptionPane.showConfirmDialog(DownloadManagerDialog.this, "Downloading process finished! \nDo you want to Exit the Download Manager?", "Done", JOptionPane.YES_NO_OPTION);
            if (okQuit == 0) {
                super.setVisible(false);
            }
        } catch (IOException e) {
            int okQuit = JOptionPane.showConfirmDialog(DownloadManagerDialog.this, "An error occured while downloading a file. The download has been stopped! \nDo you want to Exit the Download Manager?", "Error", JOptionPane.YES_NO_OPTION);
            if (okQuit == 0) {
                super.setVisible(false);
            }
        } catch (InterruptedException ie) {
            int okQuit = JOptionPane.showConfirmDialog(DownloadManagerDialog.this, "Downloading process has been cancelled! \nDo you want to Exit the Download Manager?", "Cancelled", JOptionPane.YES_NO_OPTION);
            if (okQuit == 0) {
                super.setVisible(false);
            }
        } finally {
            resetUiAfterDownload();
        }
    }
}
