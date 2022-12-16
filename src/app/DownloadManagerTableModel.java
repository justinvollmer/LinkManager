package app;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DownloadManagerTableModel extends AbstractTableModel {
    private List<LinkEntry> linkEntryList;
    private boolean isFilenameEditable = true;

    public DownloadManagerTableModel(List<LinkEntry> linkEntryList) {
        this.linkEntryList = linkEntryList;
    }

    String[] columnNames = {"ID", "Link", "Filename", "Progress"};


    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return linkEntryList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return linkEntryList.get(row).getId();
            case 1:
                return linkEntryList.get(row).getLink();
            case 2:
                return linkEntryList.get(row).getFilename();
            case 3:
                return linkEntryList.get(row).getProgress();
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 2 && isFilenameEditable) {
            return true;
        } else {
            return false;
        }
    }

    public void setFilenameEditable(boolean mode) {
        isFilenameEditable = mode;
    }

    public void setValueAt(Object value, int row, int col) {
        LinkEntry entry = linkEntryList.get(row);
        switch (col) {
            case 0:
                entry.setId((int) value);
                break;
            case 1:
                entry.setLink((String) value);
                break;
            case 2:
                entry.setFilename((String) value);
                break;
            case 3:
                entry.setProgress((String) value);
                break;
        }
    }
}
