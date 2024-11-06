package org.sopt.week2_3.Diary.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Getter
public class DiaryResponse {

    public long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long userId;


}
