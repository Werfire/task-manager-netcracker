package views;

import javax.swing.table.DefaultTableModel;

public class TasksTableModel extends DefaultTableModel {
    public TasksTableModel() {
        super(new Object[][]{}, new String[] {"Name", "DueDate", "Description", "Author", "Status"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        switch(column) {
            case 3:
            case 4:
                return false;
            default:
                return true;
        }
    }
}
