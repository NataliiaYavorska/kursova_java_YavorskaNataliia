package com.itvdn.cbs.Security.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorMessage = "Unknown error";
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "Error 404: Page not found";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Error 500: Internal server error";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "Error 403: Access denied";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                errorMessage = "Error 401: Unauthorized";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
