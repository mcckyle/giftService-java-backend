//***************************************************************************************
//
//     Filename: GiftExportDTO.java
//     Author: Kyle McColgan
//     Date: 8 December 2025
//     Description: This file holds Gift information for export purposes.
//
//***************************************************************************************

package com.mcckyle.giftplanner.dto;

import java.math.BigDecimal;

public class GiftExportDTO
{
    public Integer id;
    public String title;
    public String notes;
    public BigDecimal price;
    public Boolean purchased;
}
