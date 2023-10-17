package com.example.demo.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ItemRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {


    }

    @Test
    public void testGetItemById() throws Exception {

        long id = 1;
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/" + id)
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetItemByName() throws Exception {
        String name = "Round Widget";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/name/" + name)
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetItemByAllItem() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJwYXNzd2QzNEBiay5ydyIsImV4cCI6NTUwMzk0OTYyNTd9.V3scHiVBXvKJNSrEX3cu91VsypyrOcOVmS5CK7squ3g";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

}
