package org.sopt.Diary.service;

import org.sopt.Diary.repository.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(){
        diaryRepository.save(
                new DiaryEntity("예은"));

    }

    public List<Diary> getList(){
        // (1) repository로 부터 DiaryEntity를 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        // (2) DiaryEntity 를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getName())
            );
        }


        return diaryList;
    }
}
