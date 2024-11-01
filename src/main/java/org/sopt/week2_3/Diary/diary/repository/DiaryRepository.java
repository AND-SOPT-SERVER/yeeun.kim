package org.sopt.week2_3.Diary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByTitle(String title);

}
