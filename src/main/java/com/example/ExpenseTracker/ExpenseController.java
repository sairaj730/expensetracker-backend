package com.example.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This file is a controller that handles web requests for expenses. It defines the API for creating, getting, and deleting expenses.

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Added for CORS
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository; // Re-added UserRepository

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) { // Removed @RequestParam Long userId
        // Get userId from the expense object itself
        Long userId = expense.getUser() != null ? expense.getUser().getId() : null;
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // userId not provided in expense object
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User not found
        }
        expense.setUser(user); // Associate expense with user
        Expense savedExpense = expenseRepository.save(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(@RequestParam Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, @RequestParam Long userId) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Not found or not owned by user
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}