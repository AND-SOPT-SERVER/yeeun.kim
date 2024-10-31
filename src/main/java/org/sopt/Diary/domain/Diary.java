package org.sopt.Diary.domain;

import java.time.LocalDateTime;

public class Diary {
    private long id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public Diary(long id, String name, String content, LocalDateTime createdAt){
        this.id = id;
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
    }


    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public LocalDateTime getCreatedAt(){return createdAt; }
}

