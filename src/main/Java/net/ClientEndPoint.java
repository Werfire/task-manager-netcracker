package net;

import java.net.URI;
import javax.websocket.*;

@ClientEndpoint

public class ClientEndPoint  {
    private static Object waitLock = new Object();

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received msg: "+ message);
    }
    private static void waitForTerminateSignal()
    {
        synchronized(waitLock)
        {try {
            waitLock.wait();
        } catch (InterruptedException e) {
        }}}
    public static void main(String[] args) {
        WebSocketContainer container = null;
        Session session=null;
        try{
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
            session=container.connectToServer(ClientEndPoint.class, URI.create("ws://localhost:8080/webapp/task-manager"));
            waitForTerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(session != null){
                try {
                    session.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}