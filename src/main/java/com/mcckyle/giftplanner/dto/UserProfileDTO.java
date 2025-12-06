//***************************************************************************************
//
//     Filename: UserProfileDTO.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file holds User Profile related information in an object.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

public record UserProfileDTO(
        Integer id,
        String username,
        String email
) {}

//***************************************************************************************
