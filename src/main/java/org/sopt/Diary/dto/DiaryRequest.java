package org.sopt.Diary.dto;

public class DiaryRequest {
    private String name;
    private String content;


    public DiaryRequest() {}

    public DiaryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getContent(){
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }
}
