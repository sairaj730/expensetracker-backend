// This file is a repository for User objects. It provides methods to interact with the database for users, like finding a user by their email address.

package com.example.ExpenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
