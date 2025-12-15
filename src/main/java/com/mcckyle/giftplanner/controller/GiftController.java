//***************************************************************************************
//
//   Filename: GiftController.java
//   Author: Kyle McColgan
//   Date: 15 December 2025
//   Description: This file provides Gift functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.controller;

import com.mcckyle.giftplanner.dto.DtoMapper;
import com.mcckyle.giftplanner.model.Gift;
import com.mcckyle.giftplanner.security.UserDetailsImpl;
import com.mcckyle.giftplanner.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gifts")
public class GiftController
{
    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService)
    {
        this.giftService = giftService;
    }

    //Create a Gift.
    @PostMapping("/{personId}")
    public ResponseEntity<?> createGift(
            @PathVariable Integer personId,
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Gift gift
    )
    {
        Gift saved = giftService.createGift(user.getId(), personId, gift);

        return ResponseEntity.ok(DtoMapper.toGiftDTO(saved));
    }

    //Get Gifts for a Person.
    @GetMapping("/person/{personId}")
    public ResponseEntity<?> getGiftsForPerson(
            @PathVariable Integer personId,
            @AuthenticationPrincipal UserDetailsImpl user
    )
    {
        var gifts = giftService.getGiftsForPerson(user.getId(), personId)
                .stream()
                .map(DtoMapper::toGiftDTO)
                .toList();

        return ResponseEntity.ok(gifts);
    }

    //Update a Gift.
    @PutMapping("/{giftId}")
    public ResponseEntity<?> updateGift(
            @PathVariable Integer giftId,
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Gift updatedGift
    )
    {
        Gift saved = giftService.updateGift(user.getId(), giftId, updatedGift);

        return ResponseEntity.ok(DtoMapper.toGiftDTO(saved));
    }

    //Delete a Gift.
    @DeleteMapping("/{giftId}")
    public ResponseEntity<?> deleteGift(
            @PathVariable Integer giftId,
            @AuthenticationPrincipal UserDetailsImpl user
    )
    {
        giftService.deleteGift(user.getId(), giftId);

        return ResponseEntity.ok().build();
    }
}

//***************************************************************************************
