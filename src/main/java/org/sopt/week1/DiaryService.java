package org.sopt.week1;

import org.sopt.week1.DiaryRepository;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();


    void writeDiary(final String body){
        new Diary(null, body);
        diaryRepository.save(body);
    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

    void patchDiary(String id, String newBody){
        diaryRepository.patch(id, newBody);
    };

    // 수정, 삭제 시 id는 String 값으로 받을 것.
    void deleteDiary(final String id){
        diaryRepository.delete(id);
   };
}