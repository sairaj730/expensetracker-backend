// This file is a repository for Expense objects. It provides methods to interact with the database for expenses, like finding all expenses for a specific user.

package com.example.ExpenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
}