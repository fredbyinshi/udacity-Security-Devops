package com.example.demo.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class loginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Before
    public void setup(){

    }
    @Test
    public void TestUserLogin() throws Exception{
        String jsonReq = "{\n" +
                "\t\"username\":\"test\",\n" +
                "\t\"password\":\"passwd34@bk.rw\"\n" +
                "}";
        MvcResult resp =  mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq))
                .andReturn();
        assertNotNull(resp.getResponse().getContentAsString());
        assertEquals(200,resp.getResponse().getStatus());

    }
    @Test
    public void TestNegativeScenarioUserLogin() throws Exception{
        String jsonReq = "{\n" +
                "\t\"username\":\"test\",\n" +
                "\t\"password\":\"passwd34@bk.rw\"\n" +
                "}";
        MvcResult resp =  mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq))
                .andReturn();
        assertNotNull(resp.getResponse().getContentAsString());
        assertEquals(400,resp.getResponse().getStatus());

    }
}
