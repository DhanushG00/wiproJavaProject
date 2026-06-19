package com.wipro.auth_service.service.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.repository.UserRepo;
import com.wipro.auth_service.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPass");
        user.setRoles("ROLE_USER");
    }

    // registerUser - SUCCESS
    @Test
    void testRegisterUser_Success() {
        when(userRepo.findByUsernameIgnoreCase("testuser"))
                .thenReturn(Optional.empty());

        when(encoder.encode("password123"))
                .thenReturn("encodedPass");

        when(userRepo.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> {
            authService.registerUser("testuser", "password123", "ROLE_USER");
        });

        verify(userRepo, times(1)).save(any(User.class));
        verify(encoder, times(1)).encode("password123");
    }

    // registerUser - USERNAME EXISTS
    @Test
    void testRegisterUser_UsernameExists() {
        when(userRepo.findByUsernameIgnoreCase("testuser"))
                .thenReturn(Optional.of(user));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.registerUser("testuser", "password123", "ROLE_USER");
        });

        assertEquals("Username already exists", ex.getMessage());

        verify(userRepo, never()).save(any(User.class));
    }

    // verify username is converted to lowercase
    @Test
    void testRegisterUser_UsernameLowerCase() {
        when(userRepo.findByUsernameIgnoreCase("TESTUSER"))
                .thenReturn(Optional.empty());

        when(encoder.encode("password123"))
                .thenReturn("encodedPass");

        authService.registerUser("TESTUSER", "password123", "ROLE_USER");

        verify(userRepo).save(argThat(user ->
            user.getUsername().equals("testuser")
        ));
    }

    //  verify password encoding
    @Test
    void testRegisterUser_PasswordEncoding() {
        when(userRepo.findByUsernameIgnoreCase("testuser"))
                .thenReturn(Optional.empty());

        when(encoder.encode("password123"))
                .thenReturn("encodedPass");

        authService.registerUser("testuser", "password123", "ROLE_USER");

        verify(userRepo).save(argThat(user ->
            user.getPassword().equals("encodedPass")
        ));
    }
}
