//***************************************************************************************
//
//     Filename: PersonExportDTO.java
//     Author: Kyle McColgan
//     Date: 8 December 2025
//     Description: This file holds Person related information for export purposes.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

import java.util.List;

public class PersonExportDTO
{
    public Integer id;
    public String name;
    public String notes;

    public List<GiftExportDTO> gifts;
}
