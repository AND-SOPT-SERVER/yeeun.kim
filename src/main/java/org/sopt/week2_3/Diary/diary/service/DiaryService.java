package org.sopt.week2_3.Diary.diary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryPostDto;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryUpdateDto;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryDetailResponse;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryResponse;
import org.sopt.week2_3.Diary.diary.repository.Category;
import org.sopt.week2_3.Diary.exception.DiaryNotFoundException;
import org.sopt.week2_3.Diary.exception.DuplicateTitleException;
import org.sopt.week2_3.Diary.exception.LimitTimeException;
import org.sopt.week2_3.Diary.diary.repository.DiaryEntity;
import org.sopt.week2_3.Diary.diary.repository.DiaryRepository;
import org.sopt.week2_3.Diary.user.repository.UserEntity;
import org.sopt.week2_3.Diary.user.repository.UserRepository;
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
    private final UserRepository userRepository;

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

        if (diaryPostDto.getUserId() == 0) {
            throw new IllegalArgumentException("userId는 필수 항목입니다.");
        }

        UserEntity user = userRepository.findById(diaryPostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));


        // DiaryEntity를 빌더 패턴으로 생성
        DiaryEntity diary = DiaryEntity.builder()
                .title(diaryPostDto.getTitle())
                .content(diaryPostDto.getContent())
                .createdAt(now) // 작성 시간을 설정
                .isVisible(diaryPostDto.isVisible())
                .category(diaryPostDto.getCategory())
                .user(user)
                .build();

        diaryRepository.save(diary);
        lastDiaryCreatedAt = now;

        return "일기를 생성했습니다.";
    }

    // 일기 조회
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

    // 정렬 반영한 전체조회
    public List<DiaryResponse> getDiariesByCategoryAndSort(Category category, String sort) {
        Pageable pageable = PageRequest.of(0, 10);  // 10개씩 조회

        List<DiaryEntity> diaryEntityList = diaryRepository.findByCategoryAndSort(category, sort, pageable);

        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryResponseList.add(DiaryResponse.builder()
                    .id(diaryEntity.getId())
                    .title(diaryEntity.getTitle())
                    .content(diaryEntity.getContent())
                    .createdAt(diaryEntity.getCreatedAt())
                    .build());
        }
        return diaryResponseList;
    }




    // 내 일기 모아보기
    public List<DiaryResponse> getDiariesByUserId(long userId) {
        // 사용자 ID로 작성된 일기 중 최신순으로 정렬하여 10개만 가져옴
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<DiaryEntity> diaryEntityList = diaryRepository.findByUserId(userId, pageable);

        // DiaryEntity를 DiaryResponse로 변환하는 작업
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryResponseList.add(DiaryResponse.builder()
                    .id(diaryEntity.getId())
                    .title(diaryEntity.getTitle())
                    .build());
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
                .isVisible(diaryEntity.isVisible())
                .category(diaryEntity.getCategory())
                .build();
    }

    @Transactional
    public String updateDiary(long id, DiaryUpdateDto diaryUpdateDto){
        LocalDateTime now = LocalDateTime.now();

        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다."));

        diaryEntity.update(diaryUpdateDto.getTitle(), diaryUpdateDto.getContent(), now, diaryUpdateDto.isVisible(), diaryUpdateDto.getCategory());

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
