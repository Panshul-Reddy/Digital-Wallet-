package com.cscorner.helloapp.controller;

import com.cscorner.helloapp.model.Transaction;
import com.cscorner.helloapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactions", service.getAllTransactions());
        return "index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Transaction transaction, RedirectAttributes redirectAttributes) {
        String result = service.saveTransaction(transaction);
        if (!result.equals("success")) {
            redirectAttributes.addFlashAttribute("error", result);
        }
        return "redirect:/";
    }
}