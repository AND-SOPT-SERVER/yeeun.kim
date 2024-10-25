package org.sopt.Diary.service;

import org.sopt.Diary.domain.Diary;
import org.sopt.Diary.dto.DiaryDetailResponse;
import org.sopt.Diary.dto.DiaryRequest;
import org.sopt.Diary.repository.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(String name, String content){

        // 이름이 30자를 초과할 경우, 예외를 발생시킴
        if (name.length() > 30) {
            throw new IllegalArgumentException("일기 글자수는 최대 30자까지 가능합니다.");
        }
        diaryRepository.save(
                new DiaryEntity(name, content));

    }

    public List<Diary> getList(){
        // (1) repository로 부터 DiaryEntity를 가져옴
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")); // 작성일자 기준으로 내림차순 정렬
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll(pageable).getContent();

        // (2) DiaryEntity 를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add( // 목록으로 조회되는 일기는 제목과 id만
                    new Diary(diaryEntity.getId(), diaryEntity.getName(), diaryEntity.getContent(), diaryEntity.getCreatedAt() )
            );
        }

        return diaryList;
    }

    // 일기장 상세조회
    public DiaryDetailResponse getDiaryById(long id) {
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));

        return new DiaryDetailResponse(diaryEntity.getId(), diaryEntity.getName(),diaryEntity.getContent(), diaryEntity.getCreatedAt());
    }


    public void updateDiary(long id, DiaryRequest diaryRequest){
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));
        // 수정할 내용 검증
        String name = diaryRequest.getName();
        String content = diaryRequest.getContent();

        if (name != null && name.length() > 30) {
            throw new IllegalArgumentException("일기 글자수는 최대 30자까지 가능합니다.");
        }

        // 변경된 값이 있는 경우에만 엔티티 수정
        if (name != null) {
            diaryEntity.setName(name);
        }
        if (content != null) {
            diaryEntity.setContent(content);
        }

        diaryRepository.save(diaryEntity); // 변경된 엔티티 저장


    }



    public void deleteDiary(long id){
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));
        diaryRepository.delete(diaryEntity);

    }

}
