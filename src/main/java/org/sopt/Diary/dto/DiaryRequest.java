package org.sopt.Diary.dto;

public class DiaryRequest {

    private String name;

    private String content;

    public DiaryRequest() {}

    public DiaryRequest(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
