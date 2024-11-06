package org.sopt.week2_3.Diary.diary.dto.response;

import lombok.Builder;
import lombok.Getter;


import java.util.List;

@Builder
@Getter
public class DiaryListResponse {
    private final List<DiaryResponse> diaryList;

    public DiaryListResponse(List<DiaryResponse> diaryList) {
        this.diaryList = diaryList;
    }

    public List<DiaryResponse> getDiaryList() {

        return diaryList;
    }
}
