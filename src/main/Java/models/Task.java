package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {
    UUID id;
    String name;
    String description;
    @JsonFormat(pattern = "yyyy dd.MM HH:mm")
    LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy dd.MM HH:mm")
    LocalDateTime dueDate;
    UUID authorId;
    String statusId;

    public Task(UUID idTask, String nameTask, String description, LocalDateTime creationDate, LocalDateTime dueDate, UUID authorId, String statusId) {
        this.id = idTask;
        this.name = nameTask;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.authorId = authorId;
        this.statusId = statusId;
    }

    public Task(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.creationDate = task.getCreationDate();
        this.dueDate = task.getDueDate();
        this.authorId = task.getAuthorId();
        this.statusId = task.getStatusId();
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

    public UUID getAuthorId() {
        return authorId;
    }

    public String getStatusId() {
        return statusId;
    }

    public String[] toStringArray() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
        return new String[] {name, dueDate.format(formatter), description, authorId.toString(), String.valueOf(statusId)};
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
}