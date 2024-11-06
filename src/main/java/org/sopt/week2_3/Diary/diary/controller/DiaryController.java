package org.sopt.week2_3.Diary.diary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryPostDto;
import org.sopt.week2_3.Diary.diary.dto.request.DiaryUpdateDto;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryDetailResponse;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryListResponse;
import org.sopt.week2_3.Diary.diary.dto.response.DiaryResponse;
import org.sopt.week2_3.Diary.diary.repository.Category;
import org.sopt.week2_3.Diary.exception.DiaryNotFoundException;
import org.sopt.week2_3.Diary.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/diary")
    public ResponseEntity<String> createDiary(@Valid @RequestBody DiaryPostDto diaryPostDto) {
        try {
            String result = diaryService.createDiary(diaryPostDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // 카테고리와 정렬 기준에 따라 일기 조회 + 페이지네이션
    @GetMapping("/diary")
    public ResponseEntity<DiaryListResponse> getDiariesByCategory(
            @RequestParam Category category,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @RequestParam(defaultValue = "10") int size // 페이지 크기
    ) {
        List<DiaryResponse> diaryResponseList = diaryService.getDiariesByCategoryAndSort(category, sort, page, size);
        DiaryListResponse diaryListResponse = new DiaryListResponse(diaryResponseList);
        return ResponseEntity.ok(diaryListResponse);
    }

    // 내 일기 조회 + 정렬 + 페이지네이션 적용
    @GetMapping("/diary/me/{userId}")
    public ResponseEntity<DiaryListResponse> getMyDiariesByCategory(
            @PathVariable long userId,
            @RequestParam Category category,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<DiaryResponse> diaryResponseList = diaryService.getDiariesByUserIdAndCategoryAndSort(userId, category, sort, page, size);
        DiaryListResponse diaryListResponse = new DiaryListResponse(diaryResponseList);
        return ResponseEntity.ok(diaryListResponse);
    }

    // 상세조회
    @GetMapping("/diary/{id}")
    public ResponseEntity<DiaryDetailResponse> getDiary(@PathVariable long id) {
        try {
            DiaryDetailResponse diaryDetail = diaryService.getDiaryById(id);
            return ResponseEntity.ok(diaryDetail);
        } catch (DiaryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/diary/{id}")
    public ResponseEntity<String> updateDiary(@PathVariable long id, @Valid @RequestBody DiaryUpdateDto diaryUpdateDto) {
        try {
            String result = diaryService.updateDiary(id, diaryUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DiaryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/diary/{id}")
    public ResponseEntity<String> deleteDiary(@PathVariable long id) {
        String result = diaryService.deleteDiary(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
