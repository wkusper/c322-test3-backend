package edu.iu.c322.test3.controllers;

import edu.iu.c322.test3.repository.CustomerRepository;
import edu.iu.c322.test3.model.Customer;
import edu.iu.c322.test3.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {
    CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public AuthenticationController(CustomerRepository
                                            customerRepository,
                                    AuthenticationManager authenticationManager,
                                    TokenService tokenService) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
    @PostMapping("/signup")
    public void signup(@RequestBody Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody Customer customer) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.username(), customer.password()));
        return tokenService.generateToken(authentication);
    }
}