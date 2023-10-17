package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.errorResponse;
import com.example.demo.services.PasswordService;
import com.example.demo.testSetupUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    @Autowired
    private PasswordService passwordService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        userController = new UserController(passwordService,userRepo,cartRepo);
        testSetupUtil.InjectObjects(userController, "userRepository", userRepo);
        testSetupUtil.InjectObjects(userController, "cartRepository", cartRepo);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(passwordService,userRepo,cartRepo)).build();
    }
    @Test
    public void TestPositiveUserCreation() {
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername("test");
        user.setPassword("passwd343434yuyuyu@bk.rw");
        ResponseEntity<User> resp = userController.createUser(user);
        assertNotNull(resp);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(resp.getBody().getUsername(), "test");
        assertTrue(passwordService.verifyPassword("passwd343434yuyuyu@bk.rw", resp.getBody().getPassword()));
    }

    @Test
    public void TestNegativeScenarioUserCreation() {
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername("test");
        user.setPassword("passwd");
        ResponseEntity<errorResponse> resp = userController.createUser(user);
        assertNotNull(resp);
        assertEquals(400, resp.getStatusCodeValue());
        assertThat(resp.getBody().getMessage(), containsString("Passwd must be longer that 8 characters"));
    }

    @Test
    public void TestGetUserByUsername() throws Exception {
        String username = "test";
        mockMvc.perform(get("/api/user/username/"+username))
                .andExpect(status().isNotFound());
    }
}
