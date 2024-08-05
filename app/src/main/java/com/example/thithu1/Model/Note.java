package com.example.thithu1.Model;

public class Note {
    private long id;
    private String content;
    private String date;

    public Note(long id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
