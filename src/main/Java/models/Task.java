package models;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Task implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private int authorId;
    private int statusId;

    public Task(UUID idTask, String nameTask, String description, LocalDateTime creationDate, LocalDateTime dueDate, int authorId, int statusId) {
        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }
    public Task(){}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getStatusId() {
        return statusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,description,creationDate,dueDate,authorId,statusId);
    }

    @Override
    public boolean equals(Object obj) {
       if(obj == null || getClass() != obj.getClass())
           return false;
       Task other = (Task) obj;
       return Objects.equals(id,other.id) && Objects.equals(name,other.name) && Objects.equals(description,other.description)
               && Objects.equals(creationDate,other.creationDate) && Objects.equals(dueDate,other.dueDate) &&
               Objects.equals(authorId,other.authorId) && Objects.equals(statusId,other.statusId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("creationDate", creationDate)
                .add("dueDate", dueDate)
                .add("authorId", authorId)
                .add("statusId", statusId)
                .toString();
    }
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        Task task = (Task) object;
//        return id == task.id &&
//                authorId == task.authorId &&
//                statusId == task.statusId &&
//                Objects.equals(name, task.name) &&
//                Objects.equals(description, task.description) &&
//                Objects.equals(creationDate, task.creationDate) &&
//                Objects.equals(dueDate, task.dueDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, creationDate, dueDate, authorId, statusId);
//    }
//
//    @Override
//    public String toString() {
//        return (id + "\t" + name + "\t" + description + "\t" + creationDate.toString() + "\t" +
//                dueDate.toString() + "\t" + authorId + "\t" + statusId + "\n");
//    }
}