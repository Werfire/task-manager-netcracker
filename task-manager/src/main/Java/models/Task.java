package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
public class Task implements Serializable {
    private int id;
    private String name;
    private String description;
    private Date creationDate;
    private Date dueDate;
    private int authorId;
    private int statusId;

    public Task(int idTask, String nameTask, String description, Date creationDate, Date dueDate, int authorId, int statusId) {
        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }
    public Task(){}
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getStatusId() {
        return statusId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id &&
                authorId == task.authorId &&
                statusId == task.statusId &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(creationDate, task.creationDate) &&
                Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creationDate, dueDate, authorId, statusId);
    }

    @Override
    public String toString() {
        return (id + "\t" + name + "\t" + description + "\t" + creationDate.toString() + "\t" +
                dueDate.toString() + "\t" + authorId + "\t" + statusId + "\n");
    }
}