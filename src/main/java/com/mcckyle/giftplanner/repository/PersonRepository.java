//***************************************************************************************
//
//     Filename: PersonRepository.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file provides database connectivity
//                  for the Person class.
//
//***************************************************************************************

package com.mcckyle.giftplanner.repository;

import com.mcckyle.giftplanner.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer>
{
    List<Person> findByOwnerId(Integer ownerId);
}

//***************************************************************************************