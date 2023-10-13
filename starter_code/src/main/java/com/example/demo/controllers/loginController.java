package com.example.demo.controllers;

import com.example.demo.auth.JwtUtil;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.errorResponse;
import com.example.demo.model.requests.loginRequest;
import com.example.demo.model.requests.loginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class loginController {

    private final AuthenticationManager authenticationManager;
    public static final Logger logger = LoggerFactory.getLogger(loginController.class);
    User useDetails;
    private JwtUtil jwtUtil;
    public loginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody loginRequest loginReq)  {
        logger.info("login username >>>"+" "+loginReq.getUsername());
        logger.info("login passwd:"+""+ loginReq.getPassword());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginReq.getUsername(), loginReq.getPassword()));

            User user = new User();
            user.setUsername(loginReq.getUsername());
            user.setPassword(loginReq.getPassword());
            String token = jwtUtil.createToken(user);
            loginResponse loginRes = new loginResponse(loginReq.getUsername(),token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            errorResponse errorResponse = new errorResponse(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            errorResponse errorResponse = new errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}