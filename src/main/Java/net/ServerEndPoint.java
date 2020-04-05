package net;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import models.MutableTask;
import models.TasksModel;
import models.Task;
import util.Message;
import  util.NotificationsScheduler;


@ServerEndpoint("/websocket")
public class ServerEndPoint {
    HashMap<UUID, MutableTask> journal = new TasksModel().getJournal();
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException, EncodeException {

//        System.out.println("Received: " + message);
//        session.getBasicRemote().sendText("This is the server message!");
    }

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        System.out.println("Connection is opened");
        for(HashMap.Entry<UUID, MutableTask> entry : journal.entrySet()){
            NotificationsScheduler.scheduleNotifications(entry.getValue(), session);
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection is closed");
    }
}