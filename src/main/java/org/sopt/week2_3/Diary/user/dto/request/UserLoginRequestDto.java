package org.sopt.week2_3.Diary.user.dto.request;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    private String username;
    private String password;
}