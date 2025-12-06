//***************************************************************************************
//
//     Filename: UserServiceTest.java
//     Author: Kyle McColgan
//     Date: 5 December 2025
//     Description: This file contains User-related unit tests.
//
//***************************************************************************************

package com.mcckyle.giftplanner;

import com.mcckyle.giftplanner.dto.UserRegistrationDTO;
import com.mcckyle.giftplanner.model.Role;
import com.mcckyle.giftplanner.model.User;
import com.mcckyle.giftplanner.repository.RoleRepository;
import com.mcckyle.giftplanner.repository.UserRepository;
import com.mcckyle.giftplanner.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    //#1. registerUser() throws an error if the username already exists.
    @Test
    void registerUser_throws_ifUsernameExists()
    {
        UserRegistrationDTO dto = new UserRegistrationDTO("a", "e", "p");

        when(userRepository.existsByUsername("a")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(dto));
    }

    //#2. registerUser() throws an error if the email already exists.
    @Test
    void registerUser_throws_ifEmailExists()
    {
        UserRegistrationDTO dto = new UserRegistrationDTO("a", "e", "p");

        when(userRepository.existsByEmail("e")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(dto));
    }

    //#3. registerUser() assigns ROLE_USER and saves successfully.
    @Test
    void registerUser_savesWithRoleUser()
    {
        UserRegistrationDTO dto = new UserRegistrationDTO("a", "e", "p");

        when(userRepository.existsByUsername("a")).thenReturn(false);
        when(userRepository.existsByEmail("e")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");

        Role roleUser = new Role("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser);

        User saved = new User("a", "e", "hashed");
        saved.setRoles(Set.of(roleUser));

        when(userRepository.save(any())).thenReturn(saved);

        User result = userService.registerUser(dto);

        assertEquals("hashed", result.getPassword());
        assertTrue(result.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_USER")));
    }

    //#4. findByUsername() returns the user successfully.
    @Test
    void findByUsername_returnsUser()
    {
        User user = new User();

        when(userRepository.findByUsername("x")).thenReturn(Optional.of(user));

        assertTrue(userService.findByUsername("x").isPresent());
    }

    //#5. findById() returns the user successfully.
    @Test
    void findById_returnsUser()
    {
        User user = new User();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertTrue(userService.findById(1).isPresent());
    }

    //#6. initRoles() creates roles when empty.
    @Test
    void initRoles_createsDefaultRoles()
    {
        when(roleRepository.count()).thenReturn(0L);

        userService.initRoles();

        verify(roleRepository, times(2)).save(any(Role.class));
    }

    //#7. userExists() returns true when the user exists.
    @Test
    void userExists_returnsTrue()
    {
        when(userRepository.existsById(1)).thenReturn(true);

        assertTrue(userService.userExists(1));
    }

    //#8. updatePassword() saves hashed password successfully.
    @Test
    void updatePassword_updatesHashedPassword()
    {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("np")).thenReturn("hashed");

        userService.updatePassword(1, "np");

        assertEquals("hashed", user.getPassword());
        verify(userRepository).save(user);
    }

    //#9. deleteUserAccount() deletes successfully when exists.
    @Test
    void deleteUserAccount_deletesUser()
    {
        when(userRepository.existsById(3)).thenReturn(true);

        userService.deleteUserAccount(3);

        verify(userRepository).deleteById(3);
    }

    //#10. deleteUserAccount() throws an error if the user is missing.
    @Test
    void deleteUserAccount_throws_ifMissing()
    {
        when(userRepository.existsById(3)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> userService.deleteUserAccount(3));
    }
}
