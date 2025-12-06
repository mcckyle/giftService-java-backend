//***************************************************************************************
//
//     Filename: UserRetrievalService.java
//     Author: Kyle McColgan
//     Date: 2 December 2025
//     Description: This file provides shared user functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.service;

import com.mcckyle.giftplanner.model.User;
import java.util.Optional;

//***************************************************************************************

public interface UserRetrievalService
{
    Optional<User> findByUsername(String username);
}

//***************************************************************************************