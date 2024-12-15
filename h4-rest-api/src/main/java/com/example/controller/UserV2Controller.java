package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/users")
public class UserV2Controller {

    private final UserService userService;

    public UserV2Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getAllUsers(@RequestParam int page, @RequestParam int pageSize) {
        return userService.getAllUsers(page, pageSize);
    }

}
