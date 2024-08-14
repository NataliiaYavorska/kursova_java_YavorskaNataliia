package com.itvdn.cbs.Security.controllers;

import com.itvdn.cbs.Security.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute
    public void addUserDetailsToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PersonDetails) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            model.addAttribute("auth", personDetails.getPerson());
        }
    }

}