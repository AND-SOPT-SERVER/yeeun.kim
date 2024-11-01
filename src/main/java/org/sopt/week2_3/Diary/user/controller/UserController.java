package org.sopt.week2_3.Diary.user.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.week2_3.Diary.user.dto.UserRegisterRequestDto;
import org.sopt.week2_3.Diary.user.dto.request.UserLoginRequestDto;
import org.sopt.week2_3.Diary.user.dto.response.UserResponseDto;
import org.sopt.week2_3.Diary.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        try {
            UserResponseDto userResponse = userService.registerUser(userRegisterRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
