package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.DepositTransactionDto;
import com.eteration.simplebanking.dto.TransactionHistoryResponseDto;
import com.eteration.simplebanking.dto.WithdrawalTransactionDto;
import com.eteration.simplebanking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;


    @Test
    public void testGetAllByAccount() throws Exception {
        String accountNumber = "123456";

        // transactionService.getTransactionHistory() metodu çağrıldığında boş bir liste döndür
        when(transactionService.getTransactionHistory(accountNumber)).thenReturn((List<TransactionHistoryResponseDto>) new Object());

        mockMvc.perform(get("/getAllByAccount/123456"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).getTransactionHistory(accountNumber);
    }

    @Test
    public void testDeposit() throws Exception {
        DepositTransactionDto depositTransactionDto = new DepositTransactionDto();
        depositTransactionDto.setAmount(500.0);
        depositTransactionDto.setAccountNumber("123456");

        // JSON formatında DepositTransactionDto'yu POST isteğiyle gönderiyoruz
        mockMvc.perform(post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456\", \"amount\":500.0}"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).doTransaction(any(DepositTransactionDto.class));
    }

    @Test
    public void testWithdrawal() throws Exception {
        WithdrawalTransactionDto withdrawalTransactionDto = new WithdrawalTransactionDto();
        withdrawalTransactionDto.setAmount(200.0);
        withdrawalTransactionDto.setAccountNumber("123456");

        // JSON formatında WithdrawalTransactionDto'yu POST isteğiyle gönderiyoruz
        mockMvc.perform(post("/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456\", \"amount\":200.0}"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).doTransaction(any(WithdrawalTransactionDto.class));
    }
}
