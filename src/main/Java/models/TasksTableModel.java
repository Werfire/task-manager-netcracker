package models;

import views.ErrorDialog;
import views.ErrorType;
import views.TasksView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TasksTableModel extends DefaultTableModel {
    public TasksTableModel() {
        super(new Object[][]{}, new String[] {"Name", "DueDate", "Description", "Author", "StatusID"});
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
