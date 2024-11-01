package org.sopt.week2_3.Diary.exception;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException(String message) {
        super(message);
    }
}