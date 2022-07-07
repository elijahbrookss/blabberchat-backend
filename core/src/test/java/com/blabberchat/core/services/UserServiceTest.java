package com.blabberchat.core.services;

import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.UserRepository;
import com.blabberchat.core.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("it should return a security User with given roles, username, and password")
    void loadUserByUsername() {
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.of(mockUser));

        UserDetails securityUser = userService.loadUserByUsername(mockUser.getUsername());

        assertThat(securityUser.getUsername()).isEqualTo(mockUser.getUsername());
        assertThat(securityUser.getPassword()).isEqualTo(mockUser.getPassword());
        assertThat(securityUser.getAuthorities().size()).isEqualTo(mockUser.getRoles().size());
    }

    @Test
    @DisplayName("it should throw a UsernameNotFoundException for Nonexistent username")
    void loadUserByUsername_withNonexistentUsername() {
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loadUserByUsername(mockUser.getUsername()))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username not found");
    }

    @Test
    @DisplayName("it should create new user with correct fields")
    void createUser() {
        NewUserDTO newUserDTO = MockUtil.getNewUserDTO();

        Mockito.doReturn(null).when(userRepository).save(Mockito.any(User.class));
        Mockito.when(bCryptPasswordEncoder.encode(newUserDTO.getPassword()))
                .thenReturn("password");

        User newUser = userService.createUser(newUserDTO);

        assertThat(newUser.getUsername()).isEqualTo(newUserDTO.getUsername());
        assertThat(newUser.getPassword()).isEqualTo(newUserDTO.getPassword());
        assertThat(newUser.getEmail()).isEqualTo(newUserDTO.getEmail());
        assertThat(newUser.getProfilePicture()).isEqualTo(newUserDTO.getProfilePicture());
        assertThat(newUser.getRoles().size()).isEqualTo(newUserDTO.getRoles().size());
    }

    @Test
    @DisplayName("it should return a user that matches given username")
    void getUser() {
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.of(mockUser));

        assertThat(userService.getUser(mockUser.getUsername()))
                .isEqualTo(mockUser);
    }

    @Test
    @DisplayName("it should throw a not found exception for nonexistent username")
    void getUser_withNonexistentUsername() {
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUser(mockUser.getUsername()))
                .isInstanceOf(NoSuchElementException.class);
    }


    @Test
    @DisplayName("it should fetch all users")
    void getUsers() {
        List<User> mockUsers = List.of(MockUtil.getUser(), MockUtil.getUser(), MockUtil.getUser());

        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        assertThat(userService.getUsers().size()).isEqualTo(mockUsers.size());

    }
}