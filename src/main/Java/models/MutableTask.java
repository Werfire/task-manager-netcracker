package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class MutableTask extends Task {
    public MutableTask() {
    }

    public MutableTask(UUID idTask, String nameTask, String description, LocalDateTime creationDate,
                        LocalDateTime dueDate, UUID authorId, String statusId) {
        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }

    public MutableTask(Task task) {
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

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = LocalDateTime.of(creationDate.toLocalDate(), creationDate.toLocalTime());
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = LocalDateTime.of(dueDate.toLocalDate(), dueDate.toLocalTime());
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setStatusId(String  statusId) {
        this.statusId = statusId;
    }
}