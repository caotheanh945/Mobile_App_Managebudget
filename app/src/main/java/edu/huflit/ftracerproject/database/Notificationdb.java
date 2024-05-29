package edu.huflit.ftracerproject.database;

public class Notificationdb {
    String id;
    String content;
    String username;
    String role;
    String date;
    boolean status;

    public Notificationdb() {
    }

    public Notificationdb(String id, String content, String username, String role, String date, boolean status) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.role = role;
        this.date = date;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
