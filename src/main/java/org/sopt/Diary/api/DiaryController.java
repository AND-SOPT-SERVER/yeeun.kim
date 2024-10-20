package org.sopt.Diary.api;

import org.sopt.Diary.service.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService){
        this.diaryService = diaryService;
    }

    @PostMapping("/post")
        void post(){
        diaryService.createDiary();
    }

    @GetMapping("/posts")
            ResponseEntity<DiaryListResponse> get() {
        // (1)Serice로 부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getList();

        // (2) Client와 협의한 interface를 가져옴
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for(Diary diary : diaryList){
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getName()));
        }


        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }
}
