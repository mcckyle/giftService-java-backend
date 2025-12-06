//***************************************************************************************
//
//   Filename: PersonController.java
//   Author: Kyle McColgan
//   Date: 5 December 2025
//   Description: This file provides Person functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.controller;

import com.mcckyle.giftplanner.dto.DtoMapper;
import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.security.UserDetailsImpl;
import com.mcckyle.giftplanner.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController
{
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService)
    {
        this.personService = personService;
    }

    //Add a Person.
    @PostMapping
    public ResponseEntity<?> addPerson(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Person person
    )
    {
        Person saved = personService.createPerson(user.getId(), person);

        return ResponseEntity.ok(DtoMapper.toPersonDTO(saved));
    }

    //Get People Owned By User.
    @GetMapping
    public ResponseEntity<?> getMyPeople(@AuthenticationPrincipal UserDetailsImpl user)
    {
        var people = personService.getPersonsForUser(user.getId())
                .stream()
                .map(DtoMapper::toPersonDTO)
                .toList();

        return ResponseEntity.ok(people);
    }

    //Update a Person.
    @PutMapping("/{personId}")
    public ResponseEntity<?> updatePerson(
            @PathVariable Integer personId,
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Person updated
    )
    {
        Person saved = personService.updatePerson(user.getId(), personId, updated);

        return ResponseEntity.ok(DtoMapper.toPersonDTO(saved));
    }

    //Delete a Person.
    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePerson(
            @PathVariable Integer personId,
            @AuthenticationPrincipal UserDetailsImpl user
    )
    {
        personService.deletePerson(user.getId(), personId);

        return ResponseEntity.ok().build();
    }
}

//***************************************************************************************