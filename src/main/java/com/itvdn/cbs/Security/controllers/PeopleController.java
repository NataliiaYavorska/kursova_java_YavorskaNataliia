package com.itvdn.cbs.Security.controllers;

import com.itvdn.cbs.Security.models.Books;
import com.itvdn.cbs.Security.models.Orders;
import com.itvdn.cbs.Security.models.Person;
import com.itvdn.cbs.Security.repositories.OrdersRepository;
import com.itvdn.cbs.Security.repositories.PeopleRepository;
import com.itvdn.cbs.Security.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleRepository peopleRepository;
    private final OrdersRepository ordersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleController(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, OrdersRepository ordersRepository) {
        this.peopleRepository = peopleRepository;
        this.ordersRepository = ordersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public String getPersonInfo(@PathVariable("id") int id, Model model) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            model.addAttribute("person", person.get());

            List<Orders> orders = ordersRepository.findByPersonIdAndStatus(id, 1);

            // Извлекаем книги из заказов
            List<Books> books = orders.stream()
                    .map(Orders::getBooks)
                    .collect(Collectors.toList());

            model.addAttribute("books", books);

            return "people/index";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            peopleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Читача успішно видалено.");
            return "admin/people";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }


    @GetMapping("/detail/{id}")
    public String getPerson(@PathVariable("id") int id, Model model) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "people/detail";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "people/detail";
        }

        Optional<Person> existingPersonOptional = peopleRepository.findById(id);
        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();

            existingPerson.setUsername(person.getUsername());
            existingPerson.setYearOfBirth(person.getYearOfBirth());
            if (person.getPassword() != null && !person.getPassword().isEmpty()) {
                existingPerson.setPassword(passwordEncoder.encode(person.getPassword()));
            }
            existingPerson.setRole(person.getRole());
            peopleRepository.save(existingPerson);
            redirectAttributes.addFlashAttribute("message", "Інформація оновлена");
        } else {
            return "error/404";
        }
        return "redirect:/people/detail/" + id;
    }
}
