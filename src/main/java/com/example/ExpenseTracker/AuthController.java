package com.example.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // frontend port
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        // basic: just save directly (later you should hash password)
    
        return userRepository.save(user);
    }

   
}

