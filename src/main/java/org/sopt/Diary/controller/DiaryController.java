package org.sopt.Diary.controller;

import org.sopt.Diary.dto.DiaryDetailResponse;
import org.sopt.Diary.dto.DiaryListResponse;
import org.sopt.Diary.dto.DiaryResponse;
import org.sopt.Diary.dto.DiaryRequest;
import org.sopt.Diary.domain.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/api/diary")
    public ResponseEntity<Void> post(@RequestBody DiaryRequest diaryRequest) {
        try {
            diaryService.createDiary(diaryRequest.getName(), diaryRequest.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    // 10개만 조회
    @GetMapping("/api/diary")
    ResponseEntity<DiaryListResponse> get() {
        // (1)Serice로 부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getList();

        // (2) Client와 협의한 interface를 가져옴
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for (Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getName()));
        }
        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    // 상세조회
    @GetMapping("/api/diary/{id}")
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

    @PatchMapping("/api/diary/{id}")
    public ResponseEntity<Void> updateDiary(@PathVariable long id, @RequestBody DiaryRequest diaryRequest) {
        String name = diaryRequest.getName();
        String content = diaryRequest.getContent();

        if (name.length() > 30) {
            // 400 Bad Request를 반환하면서 에러 메시지를 전달
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일기 글자 수는 최대 30자까지 가능합니다.");
        }

        if (content.length() > 30) {
            // 400 Bad Request를 반환하면서 에러 메시지를 전달
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일기 글자 수는 최대 30자까지 가능합니다.");
        }

        try {
            diaryService.updateDiary(id, diaryRequest);
            return ResponseEntity.noContent().build(); // 204 No Content 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found 응답
        }
    }
    @DeleteMapping("/api/diary/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}
