//***************************************************************************************
//
//     Filename: GiftRepository.java
//     Author: Kyle McColgan
//     Date: 2 December 2025
//     Description: This file provides database connectivity
//                  for the Gift class.
//
//***************************************************************************************

package com.mcckyle.giftplanner.repository;

//***************************************************************************************

import com.mcckyle.giftplanner.model.Gift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends CrudRepository<Gift, Integer>
{
    List<Gift> findByPersonId(Integer personId);
}

//***************************************************************************************
