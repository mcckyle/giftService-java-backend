//***************************************************************************************
//
//     Filename: FilterConfig.java
//     Author: Kyle McColgan
//     Date: 3 December 2025
//     Description: This file holds the auth filter configuration.
//
//***************************************************************************************

package com.mcckyle.giftplanner.security.config;

import com.mcckyle.giftplanner.security.JwtAuthenticationFilter;
import com.mcckyle.giftplanner.security.UserDetailsServiceImpl;
import com.mcckyle.giftplanner.security.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//***************************************************************************************

@Configuration
public class FilterConfig
{
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService)
    {
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }
}

//***************************************************************************************