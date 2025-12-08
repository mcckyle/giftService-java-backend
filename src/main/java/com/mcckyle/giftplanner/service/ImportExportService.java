//***************************************************************************************
//
//     Filename: ImportExportService.java
//     Author: Kyle McColgan
//     Date: 8 December 2025
//     Description: This file contains business logic for importing and exporting.
//
//***************************************************************************************

package com.mcckyle.giftplanner.service;

import com.mcckyle.giftplanner.dto.ExportDTO;
import com.mcckyle.giftplanner.dto.GiftExportDTO;
import com.mcckyle.giftplanner.dto.PersonExportDTO;
import com.mcckyle.giftplanner.model.Gift;
import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.model.User;
import com.mcckyle.giftplanner.repository.GiftRepository;
import com.mcckyle.giftplanner.repository.PersonRepository;
import com.mcckyle.giftplanner.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportExportService
{
    private final PersonRepository personRepository;
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImportExportService(PersonRepository personRepository, GiftRepository giftRepository, UserRepository userRepository)
    {
        this.personRepository = personRepository;
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
    }

    //Exporting.
    public ExportDTO exportUserData(Integer userId)
    {
        List<Person> people = personRepository.findByOwnerId(userId);

        ExportDTO dto = new ExportDTO();
        dto.people = new ArrayList<>();

        for (Person p : people)
        {
            PersonExportDTO pDto = new PersonExportDTO();
            pDto.id = p.getId();
            pDto.name = p.getName();
            pDto.notes = p.getNotes();

            pDto.gifts = p.getGifts()
                    .stream()
                    .map(g -> {
                        GiftExportDTO gDto = new GiftExportDTO();
                        gDto.id = g.getId();
                        gDto.title = g.getTitle();
                        gDto.notes = g.getNotes();
                        gDto.price = g.getPrice();
                        gDto.purchased = g.isPurchased();
                        return gDto;
                    })
                    .toList();

            dto.people.add(pDto);
        }

        return dto;
    }

    //Importing.
    @Transactional
    public void importUserData(Integer userId, ExportDTO data)
    {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        //Delete existing people + gifts.
        List<Person> existingPeople = personRepository.findByOwnerId(userId);
        for (Person p : existingPeople)
        {
            giftRepository.deleteAll(p.getGifts());
        }
        personRepository.deleteAll(existingPeople);

        //Reinsert fresh data.
        for (PersonExportDTO pDto : data.people)
        {
            Person p = new Person();
            p.setName(pDto.name);
            p.setNotes(pDto.notes);
            p.setOwner(owner);

            Person savedPerson = personRepository.save(p);

            //Add gifts.
            if (pDto.gifts != null)
            {
                for (GiftExportDTO gDto : pDto.gifts)
                {
                    Gift g = new Gift();
                    g.setTitle(gDto.title);
                    g.setNotes(gDto.notes);
                    g.setPrice(gDto.price);
                    g.setPurchased(gDto.purchased);
                    g.setPerson(savedPerson);

                    giftRepository.save(g);
                }
            }
        }
    }
}
