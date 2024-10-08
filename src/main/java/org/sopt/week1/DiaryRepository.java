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

    void save(final String body) {
        // 변환 과정
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, body);
    }

    void patch(final String id, final String newBody) {
        Long longId;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format: " + id);
        }

        if (storage.containsKey(longId)) {
            storage.put(longId, newBody);
        } else {
            throw new IllegalArgumentException("No diary found with id: " + longId);
        }
    }

    void delete(final String id) {
        // Long타입의 id를 String 타입으로 변환
        Long longId; // 매개변수 String으로 받은 id값을 Long타입으로 바꾼 후 저장할 변수 선언
        try {
            longId = Long.parseLong(id); // String으로 받은 id값을 Long타입으로 변환
        } catch (NumberFormatException e) { // 예외발생 시 예외처리
            throw new IllegalArgumentException();
        }

        if (storage.remove(longId) == null) { // longId가 존재할 경우 지우고 존재하지 않는 경우
            throw new IllegalArgumentException(); // 예외를 반환
        }
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

    

}