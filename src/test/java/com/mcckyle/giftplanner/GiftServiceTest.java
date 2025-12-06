//***************************************************************************************
//
//     Filename: GiftServiceTest.java
//     Author: Kyle McColgan
//     Date: 5 December 2025
//     Description: This file contains Gift-related unit tests.
//
//***************************************************************************************

package com.mcckyle.giftplanner;

import com.mcckyle.giftplanner.model.Gift;
import com.mcckyle.giftplanner.model.Person;
import com.mcckyle.giftplanner.model.Role;
import com.mcckyle.giftplanner.model.User;
import com.mcckyle.giftplanner.repository.GiftRepository;
import com.mcckyle.giftplanner.repository.PersonRepository;
import com.mcckyle.giftplanner.service.GiftService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftServiceTest
{
    @Mock
    private GiftRepository giftRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private GiftService giftService;

    private User makeUser(int id)
    {
        User user = new User("name", "email", "pw", Set.of(new Role("ROLE_USER")));
        user.setId(id);
        return user;
    }

    //#1. Returns gifts for a person that the user owns.
    @Test
    void getGiftsForPerson_returnsGifts_whenAuthorized()
    {
        //Create the owner User.
        User ownerUser = makeUser(1);

        //Create Person beloning to this User.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Mock repository responses.
        when(personRepository.findById(10)).thenReturn(Optional.of(person));
        when(giftRepository.findByPersonId(10)).thenReturn(List.of(new Gift()));

        //Execute.
        List<Gift> result = giftService.getGiftsForPerson(1, 10);

        //Verify.
        assertEquals(1, result.size());
    }

    //#2. Throws an error when trying to access another user's person.
    @Test
    void getGiftsForPerson_throws_whenUnauthorized()
    {
        Person owner = new Person();
        owner.setOwner(new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")))
        );

        when(personRepository.findById(10)).thenReturn(Optional.of(owner));

        assertThrows(RuntimeException.class,
                () -> giftService.getGiftsForPerson(1, 10));
    }

    //#3. getById() returns a Gift when authorized.
    @Test
    void getById_returnsGift_whenAuthorized()
    {
        //Create the owner User.
        User ownerUser = makeUser(1);

        //Create Person.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Create Gift owned by this Person's owner.
        Gift gift = new Gift();
        gift.setId(55);
        gift.setPerson(person);

        //Mock the repository.
        when(giftRepository.findById(55)).thenReturn(Optional.of(gift));

        //Execute.
        Optional<Gift> result = giftService.getById(1, 55);

        //Verify.
        assertTrue(result.isPresent());
    }

    //#4. getById() throws an error when unauthorized.
    @Test
    void getById_throws_whenUnauthorized()
    {
        //Create User (owner).
        User ownerUser = makeUser(1);

        //Create Person.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Create Gift owned by this Person's owner.
        Gift gift = new Gift();
        gift.setId(55);
        gift.setPerson(person);

        //when(personRepository.findById(10)).thenReturn(Optional.of(person));

        assertThrows(RuntimeException.class,
                () -> giftService.getById(1, 55));
    }

    //#5. createGift() works if the owner matches.
    @Test
    void createGift_savesGift_whenAuthorized()
    {
        //Create the owner User.
        User ownerUser = makeUser(1);

        //Create Person.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Create Gift owned by this Person's owner.
        Gift newGift = new Gift();
        newGift.setId(55);
        newGift.setPerson(person);

        when(personRepository.findById(20)).thenReturn(Optional.of(person));
        when(giftRepository.save(any())).thenReturn(newGift);

        Gift saved = giftService.createGift(1, 20, newGift);

        assertEquals(newGift, saved);
    }

    //#6. createGift() throws an error if unauthorized.
    @Test
    void createGift_throws_whenUnauthorized()
    {
        Person person = new Person();
        person.setOwner(new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")))
        );

        when(personRepository.findById(20)).thenReturn(Optional.of(person));

        assertThrows(RuntimeException.class,
                () -> giftService.createGift(1, 20, new Gift()));
    }

    //#7. updateGift() updates all fields successfully.
    @Test
    void updatedGift_updatesFieldsCorrectly()
    {
        //Create the owner User.
        User ownerUser = makeUser(1);

        //Create Person.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Create Gift owned by this Person's owner.
        Gift gift = new Gift();
        gift.setId(55);
        gift.setPerson(person);

        Gift existing = new Gift();
        existing.setPerson(person);

        Gift updated = new Gift();
        updated.setTitle("T");
        updated.setNotes("N");
        updated.setPrice(new BigDecimal(99.99));
        updated.setUrl("http://testing-gifts.com");
        updated.setPurchased(true);

        when(giftRepository.findById(11)).thenReturn(Optional.of(existing));
        when(giftRepository.save(existing)).thenReturn(existing);

        Gift result = giftService.updateGift(1, 11, updated);

        assertEquals("T", result.getTitle());
        assertEquals("N", result.getNotes());
        assertEquals(new BigDecimal(99.99), result.getPrice());
        assertEquals("http://testing-gifts.com", result.getUrl());
        assertTrue(result.isPurchased());
    }

    //#8. updateGift() throws an error if unauthorized.
    @Test
    void updateGift_throws_whenUnauthorized()
    {
        Person person = new Person();
        person.setOwner(new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")))
        );

        Gift existing = new Gift();
        existing.setPerson(person);

        when(giftRepository.findById(11)).thenReturn(Optional.of(existing));

        assertThrows(RuntimeException.class,
                () -> giftService.updateGift(1, 11, new Gift()));
    }

    //#9. deleteGift() succeeds when authorized.
    @Test
    void deleteGift_deletes_whenAuthorized()
    {
        //Create the owner User.
        User ownerUser = makeUser(1);

        //Create Person.
        Person person = new Person();
        person.setId(10);
        person.setOwner(ownerUser);

        //Create Gift owned by this Person's owner.
        Gift gift = new Gift();
        gift.setId(55);
        gift.setPerson(person);

        when(giftRepository.findById(7)).thenReturn(Optional.of(gift));

        giftService.deletePerson(1, 7);

        verify(giftRepository).delete(gift);
    }

    //#10. deleteGift() throws an error when unauthorized.
    @Test
    void deleteGift_throws_whenUnauthorized()
    {
        Person person = new Person();
        person.setOwner(new User(
                "testUser",
                "testEmail",
                "testPassword",
                Set.of(new Role("ROLE_USER")))
        );

        Gift gift = new Gift();
        gift.setPerson(person);

        when(giftRepository.findById(7)).thenReturn(Optional.of(gift));

        assertThrows(RuntimeException.class,
                () -> giftService.deletePerson(1, 7));
    }
}
