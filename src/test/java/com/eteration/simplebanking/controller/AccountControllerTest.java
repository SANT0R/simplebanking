package com.eteration.simplebanking.controller;
import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.dto.BetweenDto;
import com.eteration.simplebanking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;



    @Test
    public void testAddAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber("123456");

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456\"}"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).add(any(AccountDto.class));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber("123456");

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456\"}"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).update(any(AccountDto.class));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        mockMvc.perform(delete("/delete")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllByOwner() throws Exception {
        String owner = "John";

        mockMvc.perform(delete("/deleteAllByOwner/John"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deleteAllByOwner(owner);
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete("/deleteAll"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deleteAll();
    }

    @Test
    public void testGetAccount() throws Exception {
        mockMvc.perform(get("/get/1"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getById(1L);
    }

    @Test
    public void testGetByAccountNumber() throws Exception {
        String accountNumber = "123456";

        when(accountService.getByAccountNumber(accountNumber)).thenReturn(new AccountDto());

        mockMvc.perform(get("/getByAccountNumber/123456"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getByAccountNumber(accountNumber);
    }

    @Test
    public void testGetByOwner() throws Exception {
        String owner = "John";

        when(accountService.getByOwner(owner)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getByOwner/John"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getByOwner(owner);
    }

    @Test
    public void testGetByBalance() throws Exception {
        BetweenDto betweenDto = new BetweenDto();
        betweenDto.setValue1(100.0);
        betweenDto.setValue2(500.0);

        when(accountService.getByBalance(100.0, 500.0)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getByBalance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value1\":100.0, \"value2\":500.0}"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getByBalance(100.0, 500.0);
    }

    @Test
    public void testGetAll() throws Exception {
        when(accountService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getAll"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAll();
    }
}