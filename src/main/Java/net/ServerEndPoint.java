package net;
import java.io.IOException;

import javax.swing.*;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import models.Task;
import util.Message;
import  util.NotificationsScheduler;

@ServerEndpoint("/websocket")
public class ServerEndPoint {
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

        System.out.println("Received: " + message);
        session.getBasicRemote().sendText("This is the server message!");
    }

    @OnOpen
    public void onOpen() {
        System.out.println("Connection is opened");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection is closed");
    }
}