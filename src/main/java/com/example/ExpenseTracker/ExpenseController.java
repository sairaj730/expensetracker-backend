package com.example.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @AuthenticationPrincipal User user, org.springframework.security.core.Authentication authentication) {
        System.out.println("[DEBUG] Authentication: " + authentication);
        System.out.println("[DEBUG] Principal: " + (authentication != null ? authentication.getPrincipal() : null));
        System.out.println("[DEBUG] User: " + user);
        if (user == null) {
            System.out.println("[DEBUG] No authenticated user found. Returning 403.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        expense.setUser(user);
        Expense savedExpense = expenseRepository.save(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(@AuthenticationPrincipal User user) {
        List<Expense> expenses = expenseRepository.findByUserId(user.getId());
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
