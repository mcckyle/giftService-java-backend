//***************************************************************************************
//
//     Filename: UserRepository.java
//     Author: Kyle McColgan
//     Date: 9 December 2025
//     Description: This file provides an interface for user-related functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.repository;

import com.mcckyle.giftplanner.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}

//***************************************************************************************
