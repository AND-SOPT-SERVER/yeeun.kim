package org.sopt.week2_3.Diary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByTitle(String title);
    List<DiaryEntity> findByUserId(long userId, Pageable pageable);

}
