package com.itvdn.cbs.Security.controllers;

import com.itvdn.cbs.Security.models.Books;
import com.itvdn.cbs.Security.models.Orders;
import com.itvdn.cbs.Security.models.Person;
import com.itvdn.cbs.Security.repositories.BooksRepository;
import com.itvdn.cbs.Security.repositories.OrdersRepository;
import com.itvdn.cbs.Security.repositories.PeopleRepository;
import com.itvdn.cbs.Security.services.AdminService;
import com.itvdn.cbs.Security.services.BookService;
import com.itvdn.cbs.Security.services.OrderService;
import com.itvdn.cbs.Security.services.RegistrationService;
import com.itvdn.cbs.Security.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RegistrationService registrationService;
    private final BookService bookService;
    private final PersonValidator personValidator;
    private final BooksRepository booksRepository;
    private final AdminService adminService;
    private final OrdersRepository ordersRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public AdminController(PeopleRepository peopleRepository, RegistrationService registrationService, AdminService adminService, BookService bookService,
                           PersonValidator personValidator, BooksRepository booksRepository, OrdersRepository ordersRepository) {
        this.adminService = adminService;
        this.registrationService = registrationService;
        this.bookService = bookService;
        this.personValidator = personValidator;
        this.booksRepository = booksRepository;
        this.ordersRepository = ordersRepository;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "admin/registration";
    }

    @GetMapping("/books")
    public String booksPage(@ModelAttribute("person") Person person, Model model) {
        List<Books> books = booksRepository.findAll();
        model.addAttribute("books", books);

        List<Object[]> booksWithStatus = booksRepository.findBooksWithStatus();
        model.addAttribute("booksWithStatus", booksWithStatus);
        return "admin/books";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/admin/registration";
        redirectAttributes.addFlashAttribute("message", "Читач успішно додан");
        registrationService.register(person);

        return "redirect:/admin/people";
    }

    @GetMapping("/add-book")
    public String addBookPage(@ModelAttribute("book") Books books) {
        return "admin/add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") @Valid Books books, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "/admin/add-book";
        bookService.register(books);

        redirectAttributes.addFlashAttribute("message", "Книгу успішно додано.");
        return "redirect:/admin/books";
    }

    @GetMapping("/add-order")
    public String addOrderPage(@ModelAttribute("order") Orders orders, Model model) {
        List<Books> availableBooks = booksRepository.findAvailableBooks();
        model.addAttribute("availableBooks", availableBooks);

        List<Person> people = peopleRepository.findAll();
        model.addAttribute("people", people);

        return "admin/add-order";
    }

    @PostMapping("/add-order")
    public String addOrderPage(@ModelAttribute("order") @Validated Orders orders,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            List<Books> availableBooks = booksRepository.findAvailableBooks();
            model.addAttribute("availableBooks", availableBooks);

            List<Person> people = peopleRepository.findAll();
            model.addAttribute("people", people);
            return "/admin/add-order";
        }

        orders.setStatus(1);
        orders.setDate(LocalDateTime.now());
        ordersRepository.save(orders);

        redirectAttributes.addFlashAttribute("message", "Книгу успішно додано до замовлень.");

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String adminPage(Model model) {
        adminService.doAdminStuff();
        List<Orders> orders = ordersRepository.findByStatus(1);
        model.addAttribute("orders", orders);
        return "/admin/dashboard";
    }

    @GetMapping("/people")
    public String peoplePage(Model model) {
        adminService.doAdminStuff();
        List<Person> people = peopleRepository.findAll();
        model.addAttribute("people", people);

        List<Books> books = booksRepository.findAll();
        model.addAttribute("books", books);
        return "/admin/people";
    }

    @GetMapping("/")
    public String redirectToDashboard(Model model) {
        return "redirect:/admin/dashboard";
    }
}
