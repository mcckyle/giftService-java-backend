//***************************************************************************************
//
//   Filename: ImportExportController.java
//   Author: Kyle McColgan
//   Date: 8 December 2025
//   Description: This file provides import and export functionality.
//
//***************************************************************************************

package com.mcckyle.giftplanner.controller;

import com.mcckyle.giftplanner.dto.ExportDTO;
import com.mcckyle.giftplanner.security.UserDetailsImpl;
import com.mcckyle.giftplanner.service.ImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportExportController
{
    private final ImportExportService service;

    @Autowired
    public ImportExportController(ImportExportService service)
    {
        this.service = service;
    }

    @GetMapping("/api/export")
    public ResponseEntity<?> exportAll(@AuthenticationPrincipal UserDetailsImpl user)
    {
        ExportDTO exportData = service.exportUserData(user.getId());
        return ResponseEntity.ok(exportData);
    }

    @PostMapping("/api/import")
    public ResponseEntity<?> importAll(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody ExportDTO data
    )
    {
        service.importUserData(user.getId(), data);
        return ResponseEntity.ok().build();
    }
}
