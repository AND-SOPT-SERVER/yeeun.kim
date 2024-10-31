package org.sopt.Diary.repository;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;


import java.time.LocalDateTime;


@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(length = 30) // 길이를 30자로 제한
    public String name;

    @Column
    public String content;

    @Column
    private LocalDateTime createdAt; // 생성일자 추가


    public DiaryEntity(){
        this.createdAt = LocalDateTime.now(); // 생성 시점에 현재 시간 저장
    }

    public DiaryEntity(String name, String content){
        this.name = name;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }


    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }

    public String getContent(){
        return content;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
