package org.sopt.week1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    // Map에 Long 타입과 String타입으로 저장됨.
    private final AtomicLong numbering = new AtomicLong();
    private final Map<Long, String> deletedDiaries = new ConcurrentHashMap<>(); // 삭제된 일기를 저장할 리스트

    void save(final String body) {
        // 변환 과정
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, body);
    }

    void patch(final Long longId, final String newBody) {
        if (storage.containsKey(longId)) {
            storage.put(longId, newBody);
        } else {
            throw new IllegalArgumentException();
        }
    }

    void delete(final Long longId) {
        String removedDiary = storage.remove(longId);
        if (removedDiary == null) {
            throw new IllegalArgumentException("삭제할 일기가 존재하지 않습니다.");  // 예외 발생

        }
        // 삭제된 일기를 deletedDiaries에 저장
        deletedDiaries.put(longId, removedDiary);
    }
    List<Diary> findAll() {
        // (1) diaryList를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();

        // (2) 저장한 값을 불러오는 반복구조
        for (long index = 1; index <= numbering.longValue(); index++) {
            final String body = storage.get(index);

            // (2-1) 불러온 값을 구성한 자료구조로 이관
            if (body != null) {
                diaryList.add(new Diary(index, body));
            }
        }

        // 불러온 자료구조를 할당
        return diaryList;
    }

    void restore(final Long longId) {
        String body = deletedDiaries.remove(longId); // 삭제된 일기 복원
        if (body != null) {
            storage.put(longId, body); // 원래의 스토리지에 복원
        } else {
            throw new IllegalArgumentException();
        }
    }


}