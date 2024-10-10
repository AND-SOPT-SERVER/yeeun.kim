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
        Long longId = parseId(id);
        diaryRepository.patch(longId, newBody);
    }

    void deleteDiary(final String id) {
        Long longId = parseId(id);
        diaryRepository.delete(longId);
    }

    // todo : 삭제된 내용 복구하는 기능, 기존 id와 함께 복구되도록 설정할 것 .
    // todo : RESTORE 명령어를 통해서 복구
    void restoreDiary(String id) {
        Long longId = parseId(id);
        diaryRepository.restore(longId);
    }

    private Long parseId(String id) {
        try {
            return Long.parseLong(id); // String을 Long으로 변환
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
