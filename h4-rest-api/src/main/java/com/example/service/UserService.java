package com.example.service;

import com.example.error.UserNotFoundException;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.repo.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getName(), user.getSurname()))
                .orElseThrow(() -> new UserNotFoundException("User not found" + id));
    }

    public void addUser(UserDto userDto) {
        userRepository.save(new User(userDto.name(), userDto.surname()));
    }

    public void updateUser(UserDto userDto, Long id) {
        userRepository.save(new User(id, userDto.name(), userDto.surname()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers() {
        return getAllUsers(0, 10);
    }

    public List<UserDto> getAllUsers(int page, int pageSize) {
        return userRepository.findAll(PageRequest.of(page, pageSize))
                .stream()
                .map(user -> new UserDto(user.getName(), user.getSurname()))
                .collect(Collectors.toList());
    }
}


