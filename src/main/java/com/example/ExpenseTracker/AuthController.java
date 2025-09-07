package com.example.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, jakarta.servlet.http.HttpServletRequest request) {
        System.out.println("[DEBUG] ---- LOGIN ENDPOINT CALLED ----");
        System.out.println("[DEBUG] Request body: email=" + loginRequest.getEmail() + ", password=" + loginRequest.getPassword());
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            System.out.println("[DEBUG] No user found with email: " + loginRequest.getEmail());
        } else {
            System.out.println("[DEBUG] Found user: " + user.getEmail() + ", hashed password: " + user.getPassword());
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jakarta.servlet.http.HttpSession session = request.getSession(true); // Create session if not exists
            System.out.println("[DEBUG] Session created: " + (session != null));
            if (session != null) {
                System.out.println("[DEBUG] Session ID: " + session.getId());
                System.out.println("[DEBUG] Session is new: " + session.isNew());
            }
            User authenticatedUser = (User) authentication.getPrincipal();
            System.out.println("User logged in: " + authenticatedUser.getEmail());
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception ex) {
            System.out.println("[DEBUG] Authentication failed: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

