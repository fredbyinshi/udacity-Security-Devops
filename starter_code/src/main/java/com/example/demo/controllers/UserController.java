package com.example.demo.controllers;

import com.example.demo.model.requests.errorResponse;
import com.example.demo.services.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(PasswordService passwordService, UserRepository userRepository, CartRepository cartRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        logger.info("getting user details by id:" + id);
        logger.info("User details" + ResponseEntity.of(userRepository.findById(id)));
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        logger.info("getting user details by username:" + username);
        User user = userRepository.findByUsername(username);
        logger.info("User details" + ResponseEntity.ok(user));
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("new/create")
    public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
        logger.info("Creating user");
        logger.info("User creation request:" + createUserRequest.getUsername());
        User user = new User();
        Cart cart = new Cart();
        if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
            logger.error("User creation failed :Username must be unique "+createUserRequest.getUsername()+" is already found in our Database");
            errorResponse errorRes = new errorResponse(HttpStatus.CONFLICT, "Username already found");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorRes);
        } else {
            if (!passwordService.validatePasswd(createUserRequest.getPassword())) {
                logger.error("password violated the standards of the passwd");
                errorResponse errorResponse = new errorResponse(HttpStatus.BAD_REQUEST, "Passwd must be longer" +
                        " that 8 characters of have special characters");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            user.setUsername(createUserRequest.getUsername());
            user.setPassword(passwordService.getBCryptedPasswd(createUserRequest.getPassword()));
            cartRepository.save(cart);
            logger.info("created User:" + user);
            user.setCart(cart);
            userRepository.save(user);
            logger.info("User registration is successfull:");
        }
        return ResponseEntity.ok(user);
    }

}
