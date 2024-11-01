package org.sopt.week2_3.Diary.user.service;

import org.sopt.week2_3.Diary.user.dto.UserRegisterRequestDto;
import org.sopt.week2_3.Diary.user.dto.request.UserLoginRequestDto;
import org.sopt.week2_3.Diary.user.repository.UserRepository;
import org.sopt.week2_3.Diary.user.repository.UserEntity;
import org.sopt.week2_3.Diary.user.dto.response.UserResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userRepository.findByUsername(userRegisterRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        UserEntity user = UserEntity.builder()
                .username(userRegisterRequestDto.getUsername())
                .password(userRegisterRequestDto.getPassword())
                .usernickname(userRegisterRequestDto.getUsernickname())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .usernickname(savedUser.getUsernickname())
                .build();
    }


}
