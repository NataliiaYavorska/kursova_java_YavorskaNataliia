package com.itvdn.cbs.Security.controllers;

import com.itvdn.cbs.Security.models.Orders;
import com.itvdn.cbs.Security.models.Person;
import com.itvdn.cbs.Security.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.itvdn.cbs.Security.security.PersonDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    private final OrdersRepository ordersRepository;

    @Autowired
    public HomeController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PersonDetails) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            Person person = personDetails.getPerson();
            // Отримуємо всі замовлення поточного користувача
            List<Orders> orders = ordersRepository.findByPersonIdAndStatus(person.getId(), 1);

            model.addAttribute("orders", orders);
        }

        return "home";
    }

    @GetMapping("/")
    public String redirectToHello() {
        return "redirect:/home";
    }
}
