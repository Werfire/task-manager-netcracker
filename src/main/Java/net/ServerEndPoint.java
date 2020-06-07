package net;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import javax.ejb.Local;
import javax.swing.text.html.HTMLDocument;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.Response;

import javassist.bytecode.Descriptor;
import models.MutableTask;
import models.TasksModel;
import models.Task;
import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.JsonIO;
import util.Message;
import  util.NotificationsScheduler;


@ServerEndpoint("/websocket")
public class ServerEndPoint {
    HashMap<UUID, MutableTask> journal = (HashMap<UUID, MutableTask>) JsonIO.readTasksFromDB();
    public ServerEndPoint() throws IOException {
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException, EncodeException {

//        System.out.println("Received: " + message);
//        session.getBasicRemote().sendText("This is the server message!");

    }

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException, ParseException {
        System.out.println("Connection is opened");
//        String url = "http://localhost:8080/rest/api/tasks";
//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.setRequestMethod("GET");
//        Object obj = new JSONParser().parse(String.valueOf(connection.getInputStream()));
//        JSONArray jsonArray = (JSONArray) obj;
//        for (Object o : jsonArray) {
//            JSONObject temp = (JSONObject) o;
//            MutableTask curTask = (MutableTask) new Task((UUID) temp.get("id"), (String) temp.get("name"), (String) temp.get("description"), (LocalDateTime) temp.get("creationDate"), (LocalDateTime) temp.get("dueDate"), (UUID) temp.get("authorId"), (String) temp.get("statusId"));
//            journal.put((UUID)temp.get("id"), curTask);
//        }
//        journal = (HashMap<UUID, MutableTask>) new TasksResource().getTasksJournal().getEntity();
//        session.getBasicRemote().sendText("Check");
        for (HashMap.Entry<UUID, MutableTask> entry : journal.entrySet()) {
            NotificationsScheduler.scheduleNotifications(entry.getValue(), session);
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection is closed");
    }

}