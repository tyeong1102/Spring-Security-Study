package com.example.testsecurity.service;

import com.example.testsecurity.dto.JoinDto;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto) {

        boolean isUser = userRepository.existsByUsername(joinDto.getUsername());
        if (isUser) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        data.setRole("ROLE_ADMIN");


        userRepository.save(data);
    }
}
