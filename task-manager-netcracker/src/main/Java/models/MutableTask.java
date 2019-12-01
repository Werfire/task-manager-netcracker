package models;

import java.time.LocalDateTime;
import java.util.UUID;

public class MutableTask extends Task {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private int authorId;
    private int statusId;

    private MutableTask(UUID idTask, String nameTask, String description, LocalDateTime creationDate,
                        LocalDateTime dueDate, int authorId, int statusId) {
        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }

    public MutableTask(){

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
        this.creationDate = LocalDateTime.of(dueDate.toLocalDate(), dueDate.toLocalTime());
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}