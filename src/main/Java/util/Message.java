package util;

import views.Notification;

public class Message {

    private Notification content;
    private String subject;

    public Notification getContent() {
        return content;
    }

    public void setContent(Notification content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        content.setName(subject);
    }

    public String getSubject() {
        return subject;
    }

}
