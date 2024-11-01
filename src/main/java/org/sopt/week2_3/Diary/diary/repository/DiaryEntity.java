package org.sopt.week2_3.Diary.diary.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sopt.week2_3.Diary.user.repository.UserEntity;


import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Column(name = "title", length = 30) // 길이를 30자로 제한
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "createdAt")
    private LocalDateTime createdAt; // 생성일자 추가

    public void update(String title, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;






}
