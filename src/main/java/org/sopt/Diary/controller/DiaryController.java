package org.sopt.Diary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.Diary.dto.request.DiaryPostDto;
import org.sopt.Diary.dto.request.DiaryUpdateDto;
import org.sopt.Diary.dto.response.DiaryDetailResponse;
import org.sopt.Diary.dto.response.DiaryListResponse;
import org.sopt.Diary.dto.response.DiaryResponse;
import org.sopt.Diary.exception.DiaryNotFoundException;
import org.sopt.Diary.service.DiaryService;
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

    // 최신순으로 10개만 조회
    @GetMapping("/diary")
    public ResponseEntity<DiaryListResponse> get() {
        // (1) Service로부터 최신순으로 정렬된 DiaryResponse 리스트 10개를 가져옴
        List<DiaryResponse> diaryResponseList = diaryService.getList();

        // (2) DiaryListResponse로 감싸서 클라이언트에게 응답 반환
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
