package org.sopt.week2_3.Diary.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponse {

    public long id;
    private String title;
    private long userId;


}
