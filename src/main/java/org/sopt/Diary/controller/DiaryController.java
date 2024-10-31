package org.sopt.Diary.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.Diary.dto.request.DiaryPostDto;
import org.sopt.Diary.dto.request.DiaryUpdateDto;
import org.sopt.Diary.dto.response.DiaryDetailResponse;
import org.sopt.Diary.dto.response.DiaryListResponse;
import org.sopt.Diary.dto.response.DiaryResponse;
import org.sopt.Diary.dto.request.DiaryRequest;
import org.sopt.Diary.domain.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/diary")
    public ResponseEntity<String> createDiary(@RequestBody DiaryPostDto diaryPostDto) {
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
        } catch (IllegalArgumentException e) {
            // 일기가 존재하지 않을 경우 404 Not Found 응답
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // 또는 적절한 에러 메시지를 담은 객체를 반환할 수 있습니다.
        }

    }

    @PatchMapping("/diary/{id}")
    public ResponseEntity<Void> updateDiary(@PathVariable long id, @RequestBody DiaryUpdateDto diaryUpdateDto) {
        String title = diaryUpdateDto.getTitle();
        String content = diaryUpdateDto.getContent();

        // 제목과 내용의 길이 유효성 검사
        if (title != null && title.length() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일기 제목은 최대 10자까지 가능합니다.");
        }
        if (content != null && content.length() > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일기 내용은 최대 30자까지 가능합니다.");
        }

        try {
            diaryService.updateDiary(id, diaryUpdateDto);
            return ResponseEntity.noContent().build(); // 204 No Content 응답
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일기가 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/diary/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}
