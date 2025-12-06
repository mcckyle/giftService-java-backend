//***************************************************************************************
//
//     Filename: DtoMapper.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file provides a simple static mapper for DTO files.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

import com.mcckyle.giftplanner.model.Gift;
import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.model.User;

public class DtoMapper
{
    public static PersonDTO toPersonDTO(Person person)
    {
        return new PersonDTO(person.getId(), person.getName(), person.getNotes());
    }

    public static GiftDTO toGiftDTO(Gift gift)
    {
        return new GiftDTO(
                gift.getId(),
                gift.getPerson().getId(),
                gift.getTitle(),
                gift.getNotes(),
                gift.getPrice(),
                gift.getUrl(),
                gift.isPurchased()
        );
    }

    public static UserProfileDTO toUserDTO(User user)
    {
        return new UserProfileDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}

//***************************************************************************************
