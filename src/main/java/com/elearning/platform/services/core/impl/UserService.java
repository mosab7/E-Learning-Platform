package com.elearning.platform.services.core.impl;

import com.elearning.platform.auth.AuthGroup;
import com.elearning.platform.auth.AuthGroupRepository;
import com.elearning.platform.auth.User;
import com.elearning.platform.auth.UserRepository;
import com.elearning.platform.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public void createUser(UserDto userDto) throws IllegalStateException {

        if (null != userRepository.findByUsername(userDto.getUsername())) {
            throw new IllegalStateException("There is already a userName with name " + userDto.getUsername());
        } else if (null != userRepository.findByEmail(userDto.getEmail())) {
            throw new IllegalStateException("There is already a userName with the email " + userDto.getEmail());
        }
        String username = userDto.getUsername();
        String password = new BCryptPasswordEncoder(11).encode(userDto.getPassword());
        String name = userDto.getName();
        String surname = userDto.getSurname();
        String email = userDto.getEmail();
        log.info("Getting image");
        log.info("about to upload");
        String imgUrl = userDto.getImgUrl();
        LocalDate date = LocalDate.now();
        User user = new User(username, password, name, surname, email, imgUrl, date);
        AuthGroup group = new AuthGroup();

        group.setUsername(userDto.getUsername());
        group.setAuthgroup("USER");

        userRepository.save(user);
        authGroupRepository.save(group);
    }

    public void update(User user) {
        User current = userRepository.findByUsername(user.getUsername());

        current.setName(user.getName());
        current.setSurname(user.getUsername());
        current.setEmail(user.getEmail());
        current.setImgUrl(user.getImgUrl());

        userRepository.save(current);
    }

    public void patch(User user) {
        User current = userRepository.findByUsername(user.getUsername());

        current.setDetail(user.getDetail());

        userRepository.save(current);
    }
}
