package com.example.demo.controllers;

import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.testSetupUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    private UserRepository userRepo = mock(UserRepository.class);

    private OrderRepository orderRepo = mock(OrderRepository.class);
    private OrderController orderController;
    private MockMvc mockMvc;
    @Before
    public void setup() {
        orderController = new OrderController(userRepo,orderRepo);
        testSetupUtil.InjectObjects(orderController, "userRepository", userRepo);
        testSetupUtil.InjectObjects(orderController, "orderRepository", orderRepo);
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(userRepo,orderRepo)).build();
    }

    @Test
    public void TestNegativeScenarioSubmitOrder(){
        ResponseEntity<UserOrder> resp = orderController.submit("NotAUser");
        assertNotNull(resp);
        assertEquals(404,resp.getStatusCodeValue());
    }
    @Test
    public void TestPositiveScenarioSubmitOrder(){
        ResponseEntity<UserOrder> resp = orderController.submit("test");
        assertNotNull(resp);
        assertEquals(200,resp.getStatusCodeValue());
    }
    @Test
    public void TestOrderByUsername() throws Exception{
        String username = "test";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/history/" + username)
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
}
