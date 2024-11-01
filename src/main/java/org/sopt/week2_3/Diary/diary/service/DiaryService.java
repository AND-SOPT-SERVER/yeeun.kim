package org.sopt.week2_3.Diary.diary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryPostDto;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryUpdateDto;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryDetailResponse;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryResponse;
import org.sopt.week2_3.Diary.exception.DiaryNotFoundException;
import org.sopt.week2_3.Diary.exception.DuplicateTitleException;
import org.sopt.week2_3.Diary.exception.LimitTimeException;
import org.sopt.week2_3.Diary.diary.repository.DiaryEntity;
import org.sopt.week2_3.Diary.diary.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.now;


@Component
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private LocalDateTime lastDiaryCreatedAt = null; // 마지막 일기 작성 시간

    @Transactional
    public String createDiary(DiaryPostDto diaryPostDto) {
        LocalDateTime now = now();

        if (lastDiaryCreatedAt != null && lastDiaryCreatedAt.plusMinutes(5).isAfter(now)) {
            throw new LimitTimeException("5분에 하나의 일기만 작성할 수 있습니다.");
        }

        if (diaryRepository.findByTitle(diaryPostDto.getTitle()).isPresent()) {
            throw new DuplicateTitleException("일기의 제목이 중복됩니다.");
        }

        // DiaryEntity를 빌더 패턴으로 생성
        DiaryEntity diary = DiaryEntity.builder()
                .title(diaryPostDto.getTitle())
                .content(diaryPostDto.getContent())
                .createdAt(now) // 작성 시간을 설정
                .build();

        diaryRepository.save(diary);
        lastDiaryCreatedAt = now;

        return "일기를 생성했습니다.";
    }

    public List<DiaryResponse> getList() {
        // (1) repository로부터 DiaryEntity를 가져옴 (작성일 기준 내림차순으로 정렬하여 10개까지 조회)
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll(pageable).getContent();

        // (2) DiaryEntity를 DiaryResponse로 변환하는 작업
        List<DiaryResponse> diaryResponseList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryResponseList.add(DiaryResponse.builder()
                    .id(diaryEntity.getId())
                    .title(diaryEntity.getTitle())
                    .build()
            );
        }

        return diaryResponseList;
    }

    // 일기장 상세조회
    public DiaryDetailResponse getDiaryById(long id) {
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다."));

        return DiaryDetailResponse.builder()
                .id(diaryEntity.getId())
                .title(diaryEntity.getTitle())
                .content(diaryEntity.getContent())
                .createdAt(diaryEntity.getCreatedAt())
                .build();
    }

    @Transactional
    public String updateDiary(long id, DiaryUpdateDto diaryUpdateDto){
        LocalDateTime now = LocalDateTime.now();

        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다."));

        diaryEntity.update(diaryUpdateDto.getTitle(), diaryUpdateDto.getContent(), now);

        lastDiaryCreatedAt = now;
        return "일기를 수정했습니다.";

    }



    @Transactional
    public String deleteDiary(long id){
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                        .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다."));
        diaryRepository.delete(diaryEntity);
        return "일기를 삭제했습니다.";

    }

}
