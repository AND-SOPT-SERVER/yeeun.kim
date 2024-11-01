package org.sopt.week2_3.Diary.user.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sopt.week2_3.Diary.diary.repository.DiaryEntity;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    public long id;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "usernickname", nullable = false)
    String usernickname;


    public void update(String username, String password, String usernickname){
        this.username = username;
        this.password = password;
        this.usernickname = usernickname;

    }

//    @OneToMany(mappedBy = "user")
//    private List<DiaryEntity> diaries;
}
