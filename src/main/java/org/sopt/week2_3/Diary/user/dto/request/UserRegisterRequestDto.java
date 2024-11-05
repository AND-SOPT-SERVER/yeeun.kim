package org.sopt.week2_3.Diary.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    private String username;

    private String password;

    private String nickname;
}