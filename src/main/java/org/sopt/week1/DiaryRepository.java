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

    void patch(final Long longId, final String newBody) {
        if (storage.containsKey(longId)) {
            storage.put(longId, newBody);
        } else {
            throw new IllegalArgumentException();
        }
    }

    void delete(final Long longId) {
        if (storage.remove(longId) == null) { // longId가 존재할 경우 지우기
            throw new IllegalArgumentException(); //  longId가 존재하지 않는 경우 예외를 반환
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
