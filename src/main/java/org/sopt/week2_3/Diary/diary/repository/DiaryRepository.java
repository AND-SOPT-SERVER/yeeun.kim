package org.sopt.week2_3.Diary.diary.repository;

import org.springframework.data.domain.Page;
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

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY " +
            "CASE WHEN :sort = 'order' THEN LENGTH(d.content) END DESC, " +
            "CASE WHEN :sort = 'createdAt' THEN d.createdAt END DESC")
    Page<DiaryEntity> findByCategoryAndSort(@Param("category") Category category, @Param("sort") String sort, Pageable pageable);

    Page<DiaryEntity> findByUserIdAndCategoryOrderByCreatedAtDesc(@Param("userId") long userId,
                                                                  @Param("category") Category category,
                                                                  Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.user.id = :userId AND d.category = :category ORDER BY LENGTH(d.content) DESC")
    Page<DiaryEntity> findByUserIdAndCategoryOrderByContentLengthDesc(@Param("userId") long userId,
                                                                      @Param("category") Category category,
                                                                      Pageable pageable);
}



