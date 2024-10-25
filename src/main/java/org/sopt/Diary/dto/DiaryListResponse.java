package org.sopt.Diary.dto;
import java.util.List;
public class DiaryListResponse {
    private final List<DiaryResponse> diaryList;

    public DiaryListResponse(List<DiaryResponse> diaryList) {
        this.diaryList = diaryList;
    }

    public List<DiaryResponse> getDiaryList() {

        return diaryList;
    }
}