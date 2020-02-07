package net;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/task-manager")
public class ServerEndPoint
{
    private static final Logger log = LoggerFactory.getLogger(ServerEndPoint.class);
    @OnClose
    public void onClose(Session session)
    {
        log.info("[Session {}] Session has been closed.", session.getId());
    }
    @OnError
    public void onError(Session session, Throwable t)
    {
        log.info("[Session {}] An error has been detected: {}.", session.getId(), t.getMessage());
    }

    @OnMessage
    public String onMessage(String message, Session session)
    {
        log.info("[Session {}] Sending message: {}", session.getId(), message);
        return message; // echo back the message received
    }
    @OnOpen
    public void onOpen(Session session)
    {
        log.info("[Session {}] Session has been opened.", session.getId());
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}