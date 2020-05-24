package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MutableTask extends Task {
    public MutableTask() {
        this.id = UUID.randomUUID();
        System.out.println("MutableTask()");
    }

    public MutableTask(UUID idTask, String nameTask, String description, LocalDateTime creationDate,
                        LocalDateTime dueDate, UUID authorId, String statusId) {
        System.out.println("MutableTask(....)");

        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }

    public MutableTask(Task task) {
        System.out.println("MutableTask(Task)");

        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.creationDate = task.getCreationDate();
        this.dueDate = task.getDueDate();
        this.authorId = task.getAuthorId();
        this.statusId = task.getStatusId();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
        this.creationDate = LocalDateTime.parse(creationDate, formatter);


    }

    public void setDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
        this.dueDate = LocalDateTime.parse(dueDate, formatter);


    }

    public void setDueDate(LocalDateTime dueDate) {

        this.dueDate = dueDate;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setStatusId(String  statusId) {
        this.statusId = statusId;
    }
}