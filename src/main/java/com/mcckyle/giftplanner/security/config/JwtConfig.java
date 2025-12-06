//***************************************************************************************
//
//     Filename: JwtConfig.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file contains the token configuration.
//
//***************************************************************************************

package com.mcckyle.giftplanner.security.config;

import com.mcckyle.giftplanner.security.jwt.JwtUtils;
import com.mcckyle.giftplanner.service.UserService;
import org.springframework.context.annotation.Configuration;

//***************************************************************************************

@Configuration
public class JwtConfig
{
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtConfig(JwtUtils jwtUtils, UserService userService)
    {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }
}

//***************************************************************************************