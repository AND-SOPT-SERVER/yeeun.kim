package org.sopt.Diary.dto;

import java.time.LocalDateTime;

public class DiaryResponse {

    public long id;
    private String name;


    public DiaryResponse(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

}
