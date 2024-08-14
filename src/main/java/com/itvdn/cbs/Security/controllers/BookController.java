package com.itvdn.cbs.Security.controllers;

import com.itvdn.cbs.Security.models.Books;
import com.itvdn.cbs.Security.models.Orders;
import com.itvdn.cbs.Security.models.Person;
import com.itvdn.cbs.Security.repositories.BooksRepository;
import com.itvdn.cbs.Security.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksRepository booksRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public BookController(BooksRepository booksRepository, OrdersRepository ordersRepository) {
        this.booksRepository = booksRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/detail/{id}")
    public String getBook(@PathVariable("id") int id, Model model) {
        Optional<Books> book = booksRepository.findById(id);

        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/detail";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Books> book = booksRepository.findById(id);

        if (book.isPresent()) {
            booksRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Книгу успішно видалено.");
            return "redirect:/admin/books/";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }


    @GetMapping("/{id}")
    public String getPersonInfo(@PathVariable("id") int id, Model model) {
        Optional<Books> book = booksRepository.findById(id);
        if (book.isPresent()) {
            System.out.println("book - "+book.get());
            model.addAttribute("book", book.get());

            Orders order = ordersRepository.findByBooksIdAndStatus(id, 1).stream().findFirst().orElse(null);
            if (order != null) {
                Person person = order.getPerson();
                model.addAttribute("person", person);
                model.addAttribute("order", order);
            }

            return "books/index";
        } else {
            // Обработка случая, если пользователь с таким ID не найден
            return "error/404";
        }
    }

    @GetMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Orders order = ordersRepository.findByBooksIdAndStatus(id, 1).stream().findFirst().orElse(null);
        String referer = request.getHeader("Referer");
        if (order != null) {
            LocalDateTime returnDateString = LocalDateTime.now();

            order.setStatus(2);
            order.setReturnDate(returnDateString);

            ordersRepository.save(order);
            redirectAttributes.addFlashAttribute("message", "Книга успішно видалена.");

            return "redirect:" + referer;
        }else{
            order = ordersRepository.findByBooksIdAndStatus(id, 2).stream().findFirst().orElse(null);
            if (order != null) {
                redirectAttributes.addFlashAttribute("error", "Книга не знайдена або вже видалена.");
                return "redirect:" + referer;
            }

        }

        return "redirect:/admin/dashboard/";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("book") @Valid Books book, BindingResult bindingResult,
                         @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "books/detail";
        }

        System.out.println("book: " + book);
        System.out.println("id: " + id);
        Optional<Books> existingBookOptional = booksRepository.findById(id);
        System.out.println("existingBookOptional: " + existingBookOptional);

        if (existingBookOptional.isPresent()) {
            Books existingBook = existingBookOptional.get();

            existingBook.setName(book.getName());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setYearRelease(book.getYearRelease());
            redirectAttributes.addFlashAttribute("message", "Книга оновлена.");
            booksRepository.save(existingBook);
        } else {
            redirectAttributes.addFlashAttribute("error", "Книга не оновлена.");
        }
        return "redirect:/books/detail/" + id;
    }
}
