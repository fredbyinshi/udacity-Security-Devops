package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.services.PasswordService;
import com.example.demo.testSetupUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CartControllerTest {
    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private ItemRepository itemRepo = mock(ItemRepository.class);
    private CartController cartController;
    private UserController userController;

    @Autowired
    private PasswordService passwordService;
    @Autowired
     private MockMvc mockMvc;
    @Before
    public void setup() {
        cartController = new CartController();
        testSetupUtil.InjectObjects(cartController, "userRepository", userRepo);
        testSetupUtil.InjectObjects(cartController, "cartRepository", cartRepo);
        testSetupUtil.InjectObjects(cartController, "itemRepository", itemRepo);

        userController = new UserController(passwordService, userRepo, cartRepo);
        testSetupUtil.InjectObjects(userController, "userRepository", userRepo);
        testSetupUtil.InjectObjects(userController, "cartRepository", cartRepo);
    }

    @Test
    public void TestNegativeScenarioAddCart() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1);
        cartRequest.setQuantity(2);
        cartRequest.setUsername("test");
        ResponseEntity<Cart> cartResponse = cartController.addTocart(cartRequest);
        assertNotNull(cartResponse);
        assertEquals(404, cartResponse.getStatusCodeValue());

    }

    @Test
    public void TestPositiveScenarioAddCart() throws Exception{
        String jsonReq = "{\n" +
                "\t\"username\":\"test\",\n" +
                "\t\"itemId\":\"1\",\n" +
                "\t\"quantity\":\"1\"\n" +
                "}";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
    MvcResult resp =  mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/cart/addToCart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq)
                    .header("Authorization",token))
                    .andReturn();
    assertNotNull(resp.getResponse().getContentAsString());
    assertEquals(200,resp.getResponse().getStatus());
    }
    @Test
    public void  TestPositiveScenarioRemoveCart() throws Exception{
        String jsonReq = "{\n" +
                "\t\"username\":\"test\",\n" +
                "\t\"itemId\":\"1\",\n" +
                "\t\"quantity\":\"1\"\n" +
                "}";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
        MvcResult resp =  mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/cart/removeFromCart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq)
                        .header("Authorization",token))
                .andReturn();
        assertNotNull(resp.getResponse().getContentAsString());
        assertEquals(200,resp.getResponse().getStatus());
    }

}
