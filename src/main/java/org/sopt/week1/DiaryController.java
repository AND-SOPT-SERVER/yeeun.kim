package org.sopt.week1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        try {
            diaryService.writeDiary(body);
        } catch (IllegalArgumentException e) {
            System.err.println("일기를 작성할 수 없습니다: ");
        }
    }

    final void delete(final String id) {
        try {
            diaryService.deleteDiary(id);
        } catch (IllegalArgumentException e) {
            System.err.println("일기를 삭제할 수 없습니다.");
        }
    }

    final void patch(final String id, final String newBody) {
        try {
            diaryService.patchDiary(id, newBody);
        } catch (IllegalArgumentException e) {
            System.err.println("일기를 수정할 수 없습니다.");
        }
    }

    final void restore(final String id) {
        try {
            diaryService.restoreDiary(id);
        } catch (IllegalArgumentException e) {
            System.err.println("일기를 복구할 수 없습니다.");
        }
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}