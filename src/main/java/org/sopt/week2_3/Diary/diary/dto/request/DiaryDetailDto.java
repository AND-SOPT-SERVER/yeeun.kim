package org.sopt.week2_3.Diary.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDetailDto {
    public long id;

    @NotEmpty
    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(min = 1 ,max = 10, message = "일기의 제목은 최소 1글자, 최대 10글자까지 가능합니다.")
    public String title;

    @NotEmpty
    @NotBlank(message = "내용은 필수 항목입니다.")
    @Size(min = 1 ,max = 30, message = "일기의 제목은 최소 1글자, 최대 30글자까지 가능합니다.")
    public String content;

    private LocalDateTime createdAt; // 생성일자 추가
}
