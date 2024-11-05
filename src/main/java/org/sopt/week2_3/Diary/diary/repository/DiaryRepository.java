package org.sopt.week2_3.Diary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByTitle(String title);
    List<DiaryEntity> findByUserId(long userId, Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY " +
            "CASE WHEN :sort = 'order' THEN LENGTH(d.content) END DESC, " +
            "CASE WHEN :sort = 'createdAt' THEN d.createdAt END DESC")
    List<DiaryEntity> findByCategoryAndSort(Category category, String sort, Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.user.id = :userId " +
            "ORDER BY " +
            "CASE WHEN :sort = 'order' THEN LENGTH(d.content) END DESC, " +
            "CASE WHEN :sort = 'createdAt' THEN d.createdAt END DESC")
    List<DiaryEntity> findByUserIdAndCategoryAndSort(@Param("category") Category category,
                                            @Param("userId") long userId,
                                            @Param("sort") String sort,
                                            Pageable pageable);
}


