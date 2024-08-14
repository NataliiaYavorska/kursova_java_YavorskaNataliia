package com.itvdn.cbs.Security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itvdn.cbs.Security.models.Person;
import com.itvdn.cbs.Security.services.RegistrationService;
import com.itvdn.cbs.Security.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "auth/error";
    }
}
