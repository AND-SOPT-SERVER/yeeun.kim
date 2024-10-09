package org.sopt.week1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body) {
        diaryRepository.save(body);
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    void patchDiary(String id, String newBody) {
        Long longId; // 매개변수 String으로 받은 id 값을 Long 타입으로 바꾼 후 저장할 변수 선언
        try {
            longId = Long.parseLong(id); // String을 Long으로 변환
        } catch (NumberFormatException e) { // 예외 발생 시 예외처리
            throw new IllegalArgumentException();
        }
        diaryRepository.patch(longId, newBody); // 변환된 Long ID를 Repository에 전달
    }

    void deleteDiary(final String id) {
        Long longId;
        try {
            longId = Long.parseLong(id); // String을 Long으로 변환
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        diaryRepository.delete(longId); // 변환된 Long ID를 리포지토리에 전달
    }
}
