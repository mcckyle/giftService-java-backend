//***************************************************************************************
//
//     Filename: GiftDTO.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file holds Gift related information in an object.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

import com.mcckyle.giftplanner.model.Person;
import java.math.BigDecimal;

public record GiftDTO(
        Integer id,
        Integer personId,
        String title,
        String notes,
        BigDecimal price,
        String url,
        Boolean purchased
) {}

//***************************************************************************************
