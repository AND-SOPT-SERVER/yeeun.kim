package org.sopt.Diary.exception;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException(String message) {
        super(message);
    }
}