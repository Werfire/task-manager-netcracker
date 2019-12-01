/*package views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.UUID;
import controllers.TaskController;
import models.Task;
import models.TaskModel;
import java.util.Date;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Notification extends TaskModel {
    private static HashMap<UUID,Task> curModel = getJournal();
    public static void createGUI()
    {
        LocalDateTime dateNow = LocalDateTime.now();
        for(HashMap.Entry<UUID, Task> entry : curModel.entrySet()){
            if(entry.getValue().getDueDate().isEqual(dateNow)){
                JFrame frame = new JFrame("N O T F I C A T I O N");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(100,300);
                JLabel label = new JLabel( );
                frame.getContentPane().add(label);
                frame.setPreferredSize(new Dimension(300, 100));
                frame.pack();
                frame.setVisible(true);
            }
        }

    }
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.SwingUtilities.invokeLater(() -> createGUI());
    }
}*/
