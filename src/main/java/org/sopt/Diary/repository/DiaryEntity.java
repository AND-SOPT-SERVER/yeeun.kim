package org.sopt.Diary.repository;

import jakarta.persistence.*;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column
    public String name;

    public DiaryEntity(){}

    public DiaryEntity(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }


}
