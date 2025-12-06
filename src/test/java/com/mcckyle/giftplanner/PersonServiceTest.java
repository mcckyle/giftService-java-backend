//***************************************************************************************
//
//     Filename: PersonServiceTest.java
//     Author: Kyle McColgan
//     Date: 5 December 2025
//     Description: This file contains Person-related unit tests.
//
//***************************************************************************************

package com.mcckyle.giftplanner;

import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.model.Role;
import com.mcckyle.giftplanner.model.User;
import com.mcckyle.giftplanner.repository.PersonRepository;
import com.mcckyle.giftplanner.repository.UserRepository;
import com.mcckyle.giftplanner.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest
{
    @Mock
    private PersonRepository personRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PersonService personService;

    //#1. getPersonsForUser() returns a list.
    @Test
    void getPersonsForUser_returnsList()
    {
        when(personRepository.findByOwnerId(1)).thenReturn(
                List.of(new Person(), new Person())
        );

        List<Person> result = personService.getPersonsForUser(1);
        assertEquals(2, result.size());
        verify(personRepository).findByOwnerId(1);
    }

    //#2. getById() returns present optional.
    @Test
    void getById_returnsPerson()
    {
        Person person = new Person();

        when(personRepository.findById(20)).thenReturn(Optional.of(person));

        assertTrue(personService.getById(20).isPresent());
    }

    //#3. createPerson() assigns the owner and saves successfully.
    @Test
    void createPerson_savesPerson()
    {
        User owner = new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")));

        Person person = new Person();

        when(userRepository.findById(1)).thenReturn(Optional.of(owner));
        when(personRepository.save(person)).thenReturn(person);

        Person saved = personService.createPerson(1, person);

        assertEquals(owner, saved.getOwner());
    }

    //#4. createPerson() throws an error if the user does not exist.
    @Test
    void createPerson_throws_whenOwnerMissing()
    {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personService.createPerson(1, new Person()));
    }

    //#5. updatePerson() saves the updated fields successfully.
    @Test
    void updatePerson_updatesFields()
    {
        User owner = new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER"))
        );
        owner.setId(1);

        Person existing = new Person();
        existing.setOwner(owner);

        Person updated = new Person();
        updated.setName("New");
        updated.setNotes("Notes");

        when(personRepository.findById(10)).thenReturn(Optional.of(existing));
        when(personRepository.save(existing)).thenReturn(existing);

        Person result = personService.updatePerson(1, 10, updated);

        assertEquals("New", result.getName());
        assertEquals("Notes", result.getNotes());
    }

    //#6. updatePerson() throws an error if unauthorized.
    @Test
    void updatePerson_throws_whenUnauthorized()
    {
        Person person = new Person();
        User owner = new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")));

        person.setOwner(owner);

        when(personRepository.findById(10)).thenReturn(Optional.of(person));

        assertThrows(RuntimeException.class,
                () -> personService.updatePerson(1, 10, new Person()));
    }

    //#7. updatePerson() throws an error if not found.
    @Test
    void updatePerson_throws_whenNotFound()
    {
        when(personRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personService.updatePerson(1, 10, new Person()));
    }

    //#8. deletePerson() deletes successfully when authorized.
    @Test
    void deletePerson_deletes_whenAuthorized()
    {
        User owner = new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER"))
        );
        owner.setId(1);

        Person person = new Person();
        person.setOwner(owner);

        when(personRepository.findById(5)).thenReturn(Optional.of(person));

        personService.deletePerson(1, 5);

        verify(personRepository).delete(person);
    }

    //#9. deletePerson() throws an error when unauthorized.
    @Test
    void deletePerson_throws_whenUnauthorized()
    {
        Person person = new Person();
        User owner = new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")));

        when(personRepository.findById(5)).thenReturn(Optional.of(person));

        assertThrows(RuntimeException.class,
                () -> personService.deletePerson(1, 5));
    }

    //#10. deletePerson() throws an error if not found.
    @Test
    void deletePerson_throws_whenNotFound()
    {
        when(personRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personService.deletePerson(1, 5));
    }
}
