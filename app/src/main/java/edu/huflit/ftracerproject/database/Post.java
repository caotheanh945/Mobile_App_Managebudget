package edu.huflit.ftracerproject.database;

public class Post {
    String postcontent;
    String imgname;
    String username;

    public Post() {
    }

    public Post(String imgname ,String postcontent, String username) {
        this.postcontent = postcontent;
        this.username = username;
        this.imgname = imgname;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
