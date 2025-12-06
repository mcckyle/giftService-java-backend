//***************************************************************************************
//
//     Filename: PersonDTO.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file holds Person related information in an object.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

public record PersonDTO(
        Integer id,
        String name,
        String notes
) {}

//***************************************************************************************