package models;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {
    private UUID id;
    private String username;
    private String password;

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(id,other.id) && Objects.equals(username,other.username) && Objects.equals(password,other.password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", username)
                .add("password", password)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,username,password);
    }
}
