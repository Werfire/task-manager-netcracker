package util;

import models.Task;

import views.Notification;
import javax.annotation.Nonnull;
import javax.swing.*;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;


public class NotificationsScheduler {
    public boolean[] flags;
    private static ArrayList<Timer> timers = new ArrayList<>();
    public static void resetTimers() {
        for(Timer timer : timers)
            timer.cancel();
        timers.clear();
    }

    public static void scheduleNotifications(JFrame frame, Task task) {

        if(LocalDateTime.now().compareTo(task.getDueDate().minusMinutes(1)) < 0) {
            Timer timer = new Timer();
            timers.add(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Notification(frame, task, true);
                    timers.remove(timer);
                }
            }, Date.from(task.getDueDate().minusMinutes(1)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }

        if(LocalDateTime.now().compareTo(task.getDueDate()) < 0) {
            Timer timer = new Timer();
            timers.add(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Notification(frame, task, false);
                    timers.remove(timer);
                }
            }, Date.from(task.getDueDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }
    }

    public static void scheduleNotifications(Task task, Session session){
        if(LocalDateTime.now().compareTo(task.getDueDate()) < 0) {
            Timer timer = new Timer();
            timers.add(timer);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
//                    new Notification( task, false);
                    session.getAsyncRemote().sendText(String.format("Время выполнения задачи \"%s\" подошло к концу.", task.getName()));
                    timers.remove(timer);
                }
            }, Date.from(task.getDueDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }
    }
}
