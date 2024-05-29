package edu.huflit.ftracerproject.database;

import java.sql.Timestamp;
import java.util.Date;

public class Chat {
    String chatcontent;
    String username;


    public Chat(String chatcontent, String username) {
        this.chatcontent = chatcontent;
        this.username = username;
    }

    public String getChatcontent() {
        return chatcontent;
    }

    public void setChatcontent(String chatcontent) {
        this.chatcontent = chatcontent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

