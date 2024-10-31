package org.sopt.Diary.domain;

import java.time.LocalDateTime;

public class Diary {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public Diary(long id, String title, String content, LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }


    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public LocalDateTime getCreatedAt(){return createdAt; }
}

