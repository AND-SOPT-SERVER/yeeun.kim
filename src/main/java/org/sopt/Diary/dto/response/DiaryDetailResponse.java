package org.sopt.Diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDetailResponse {

    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;


}
