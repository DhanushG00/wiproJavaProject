package com.wipro.auth_service.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.auth_service.dto.AuthRequest;
import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.repository.UserRepo;
import com.wipro.auth_service.service.IUserImpl;

@ExtendWith(MockitoExtension.class)
class IUserImplTest {

    @Mock
    private UserRepo repo;

    @InjectMocks
    private IUserImpl userService;

    private User user;
    private AuthRequest request;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setRoles("ROLE_USER");

        request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("pass");
    }

    // addUser
    @Test
    void testAddUser() {
        when(repo.save(any(User.class))).thenReturn(user);

        User result = userService.addUser(request);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(repo, times(1)).save(any(User.class));
    }

    //  getAllUsers
    @Test
    void testGetAllUsers() {
        when(repo.findAll()).thenReturn(List.of(user));

        List<AuthRequest> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        assertEquals("ROLE_USER", result.get(0).getRoles());
    }

    //  deleteUsersById
    @Test
    void testDeleteUsersById() {
        doNothing().when(repo).deleteById(1L);

        userService.deleteUsersById(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    //  findUserById - SUCCESS
    @Test
    void testFindUserById_Success() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    //  findUserById - NOT FOUND
    @Test
    void testFindUserById_NotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.findUserById(1L);
        });
    }
}