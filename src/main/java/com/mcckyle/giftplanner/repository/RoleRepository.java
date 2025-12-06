//***************************************************************************************
//
//     Filename: RoleRepository.java
//     Author: Kyle McColgan
//     Date: 2 December 2025
//     Description: This file provides database connectivity
//                  for role based access control functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.repository;

//***************************************************************************************

import com.mcckyle.giftplanner.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>
{
    Role findByName(String name); //For finding a role by its name.
}

//***************************************************************************************