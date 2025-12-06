//***************************************************************************************
//
//     Filename: GiftService.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file contains Gift-related business logic.
//
//***************************************************************************************

package com.mcckyle.giftplanner.service;

import com.mcckyle.giftplanner.model.Gift;
import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.repository.GiftRepository;
import com.mcckyle.giftplanner.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftService
{
    private final GiftRepository giftRepository;
    private final PersonRepository personRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository, PersonRepository personRepository)
    {
        this.giftRepository = giftRepository;
        this.personRepository = personRepository;
    }

    public List<Gift> getGiftsForPerson(Integer userId, Integer personId)
    {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found!"));

        if ( ! person.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized to access gifts for this person!");
        }

        return giftRepository.findByPersonId(personId);
    }

    public Optional<Gift> getById(Integer userId, Integer giftId)
    {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("Gift not found!"));

        if ( ! gift.getPerson().getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized!");
        }

        return Optional.of(gift);
    }

    @Transactional
    public Gift createGift(Integer userId, Integer personId, Gift gift)
    {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found!"));

        if ( ! person.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized!");
        }

        gift.setPerson(person);

        return giftRepository.save(gift);
    }

    @Transactional
    public Gift updateGift(Integer userId, Integer giftId, Gift updated)
    {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("Gift not found!"));

        if ( ! gift.getPerson().getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized!");
        }

        gift.setTitle(updated.getTitle());
        gift.setNotes(updated.getNotes());
        gift.setPrice(updated.getPrice());
        gift.setUrl(updated.getUrl());
        gift.setPurchased(updated.isPurchased());

        return giftRepository.save(gift);
    }

    @Transactional
    public void deletePerson(Integer userId, Integer giftId)
    {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("Gift not found!"));

        if ( ! gift.getPerson().getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized to delete this gift!");
        }

        giftRepository.delete(gift);
    }
}

//***************************************************************************************
