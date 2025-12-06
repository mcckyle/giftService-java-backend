//***************************************************************************************
//
//     Filename: PersonService.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file contains Person-related business logic.
//
//***************************************************************************************

package com.mcckyle.giftplanner.service;

import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.model.User;
import com.mcckyle.giftplanner.repository.PersonRepository;
import com.mcckyle.giftplanner.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService
{
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository)
    {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<Person> getPersonsForUser(Integer userId)
    {
        return personRepository.findByOwnerId(userId);
    }

    public Optional<Person> getById(Integer personId)
    {
        return personRepository.findById(personId);
    }

    @Transactional
    public Person createPerson(Integer userId, Person person)
    {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        person.setOwner(owner);

        return personRepository.save(person);
    }

    @Transactional
    public Person updatePerson(Integer userId, Integer personId, Person updated)
    {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found!"));

        if ( ! person.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized to update this person!");
        }

        person.setName(updated.getName());
        person.setNotes(updated.getNotes());

        return personRepository.save(person);
    }

    @Transactional
    public void deletePerson(Integer userId, Integer personId)
    {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found!"));

        if ( ! person.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Unauthorized to delete this person!");
        }

        personRepository.delete(person);
    }
}

//***************************************************************************************
